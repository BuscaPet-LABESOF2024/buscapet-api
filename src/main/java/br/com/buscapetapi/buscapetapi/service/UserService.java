package br.com.buscapetapi.buscapetapi.service;

import br.com.buscapetapi.buscapetapi.dto.input.AddressInput;
import br.com.buscapetapi.buscapetapi.dto.input.UserInput;
import br.com.buscapetapi.buscapetapi.dto.input.UserRegistrationInput;
import br.com.buscapetapi.buscapetapi.dto.input.UserUpdateRequestInput;
import br.com.buscapetapi.buscapetapi.dto.output.AddressDataProfileOutput;
import br.com.buscapetapi.buscapetapi.dto.output.UserDataProfileOutput;
import br.com.buscapetapi.buscapetapi.dto.output.UserOutput;
import br.com.buscapetapi.buscapetapi.dto.output.UserUpdateRequestOutput;
import br.com.buscapetapi.buscapetapi.model.Address;
import br.com.buscapetapi.buscapetapi.model.User;
import br.com.buscapetapi.buscapetapi.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static javax.crypto.Cipher.SECRET_KEY;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddressService addressService;
    private final ModelMapper modelMapper;
    private EmailService emailService;

    public UserService(UserRepository userRepository,
                       EmailService emailService,
                       ModelMapper modelMapper,
                       AddressService addressService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.modelMapper = modelMapper;
        this.addressService = addressService;
    }

    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        return null;  // Retorna null se o usuário não for encontrado
    }

    public UserUpdateRequestOutput updateUser(UserUpdateRequestInput userUpdateRequestInput, Long userId) {
        // Buscar o usuário pelo ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Atualiza apenas os campos que não estão nulos
        if (userUpdateRequestInput.getName() != null) {
            user.setName(userUpdateRequestInput.getName());
        }
        if (userUpdateRequestInput.getPhone() != null) {
            user.setPhone(userUpdateRequestInput.getPhone());
        }

        user.setUpdatedAt(LocalDate.now());

        // Salvar as alterações no banco de dados
        userRepository.save(user);

        // Mapear apenas os dados atualizados para o DTO de saída
        UserUpdateRequestOutput profileOutput = new UserUpdateRequestOutput();
        profileOutput.setName(user.getName());
        profileOutput.setPhone(user.getPhone());

        return profileOutput;

    }

    public String generateToken(String email) {
        // Gera uma chave segura para HS256
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 dia
                .signWith(key)
                .compact();
    }

    public boolean authenticate(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        // Verifique a senha. Idealmente, deve estar criptografada.
        return user.map(value -> value.getPassword().equals(password)).orElse(false);
    }

    public void forgotPassword(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            String token = UUID.randomUUID().toString();
            User existingUser = user.get();
            existingUser.setResetToken(token);
            userRepository.save(existingUser);
            emailService.sendResetPasswordEmail(email, token);
        }
    }

    public Optional<User> findByResetToken(String token) {
        return userRepository.findByResetToken(token);
    }

    public void updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);  // Criptografe a senha se necessário
        user.setResetToken(null);  // Limpa o token após o uso
        userRepository.save(user);
    }

    public UserDataProfileOutput getUserDataProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        // Mapeie os dados do usuário para o DTO
        UserDataProfileOutput profileOutput = new UserDataProfileOutput();
        profileOutput.setName(user.getName());
        profileOutput.setEmail(user.getEmail());
        profileOutput.setPhone(user.getPhone());

        // Mapeie o endereço, se houver
        if (user.getAddress() != null) {
            Address address = addressService.findById(user.getAddress());
            AddressDataProfileOutput addressOutput = new AddressDataProfileOutput();
            addressOutput.setStreet(address.getStreet());
            addressOutput.setNumber(address.getNumber());
            addressOutput.setNeighborhood(address.getNeighborhood());
            addressOutput.setCep(address.getCep());
            addressOutput.setReferencia(address.getReferencia());
            addressOutput.setComplemento(address.getComplemento());
            profileOutput.setAddress(addressOutput);
        }

        return profileOutput;

    }
}
