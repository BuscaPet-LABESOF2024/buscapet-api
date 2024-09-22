package br.com.buscapetapi.buscapetapi.service;

import br.com.buscapetapi.buscapetapi.model.User;
import br.com.buscapetapi.buscapetapi.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static javax.crypto.Cipher.SECRET_KEY;

@Service
public class UserService {

    private final UserRepository userRepository;
    private EmailService emailService;

    @Value("${JWT_SECRET}")
    private String secretKey;

    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public User createUser(User userInput){
        userInput.setCreatedAt(LocalDate.now());
        userInput.setUpdatedAt(LocalDate.now());
        return userRepository.save(userInput);
    }

    public User findById(Long id){
        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isPresent()){
            return existingUser.get();
        }
        return null;
    }

    public User updateUser(User userInput){
        Optional<User> existingUser = userRepository.findById(userInput.getId());

        if (existingUser.isPresent()){
            User user = existingUser.get();

            user.setName(userInput.getName());
            user.setPassword(userInput.getPassword());
            user.setAddress(userInput.getAddress());
            user.setPhone(userInput.getPhone());
            user.setCreatedAt(userInput.getCreatedAt());
            user.setUpdatedAt(LocalDate.now());

        }
        return userRepository.save(userInput);
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
