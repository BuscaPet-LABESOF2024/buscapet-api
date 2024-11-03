package br.com.buscapetapi.buscapetapi.service;

import br.com.buscapetapi.buscapetapi.dto.input.AddressInput;
import br.com.buscapetapi.buscapetapi.dto.input.UserInput;
import br.com.buscapetapi.buscapetapi.dto.input.UserRegistrationInput;
import br.com.buscapetapi.buscapetapi.dto.output.UserOutput;
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

    public User createUser(UserRegistrationInput userInput){

        User user = modelMapper.map(userInput, User.class);

        user.setCreatedAt(LocalDate.now());
        user.setUpdatedAt(LocalDate.now());
        return userRepository.save(user);
    }

    public User findById(Long id){
        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isPresent()){
            return existingUser.get();
        }
        return null;
    }

    public UserOutput updateUser(UserInput userInput) {
        Optional<User> existingUser = userRepository.findById(userInput.getId());
        User user = null;
        if (existingUser.isPresent()) {
            user = existingUser.get();

            user.setName(userInput.getName());
            user.setEmail(userInput.getEmail());
            user.setPhone(userInput.getPhone());

            // Atualizar ou criar endereço do usuário
            AddressInput addressInput = new AddressInput();
            addressInput.setId(userInput.getAddressId());
            addressInput.setStreet(userInput.getStreet());
            addressInput.setNumber(userInput.getNumber());
            addressInput.setNeighborhood(userInput.getNeighborhood());
            addressInput.setCep(userInput.getCep());
            addressInput.setUpdatedAt(LocalDateTime.now());
            addressInput.setReferencia(userInput.getReferencia());
            addressInput.setComplemento(userInput.getComplemento());

            if (userInput.getAddressId() == null) {
                // Cria um novo endereço e captura o ID gerado
                Address newAddress = addressService.createAddress(addressInput);
                user.setAddress(newAddress.getId());
            } else {
                // Atualiza o endereço existente
                Address updatedAddress = addressService.updateAddress(addressInput);
                if (updatedAddress != null) {
                    user.setAddress(updatedAddress.getId());
                }
            }

            user.setPassword(userInput.getPassword());
            user.setUpdatedAt(LocalDate.now());

            // Salva o usuário atualizado no repositório
            user = userRepository.save(user);
        }

        // Mapeia o objeto `User` atualizado para `UserOutput`
        UserOutput userOutput = modelMapper.map(user, UserOutput.class);

        // Ajusta manualmente os campos que possam não estar corretamente mapeados
        userOutput.setAddressId(user.getAddress()); // Mapeia o ID do endereço para o output
        return userOutput;
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
        if (user.isPresent()) {
            // Verifique a senha. Idealmente, deve estar criptografada.
            return user.get().getPassword().equals(password);

        }
        return false;
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

}
