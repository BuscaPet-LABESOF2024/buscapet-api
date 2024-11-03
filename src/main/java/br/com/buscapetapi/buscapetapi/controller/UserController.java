package br.com.buscapetapi.buscapetapi.controller;

import br.com.buscapetapi.buscapetapi.dto.input.UserInput;
import br.com.buscapetapi.buscapetapi.dto.input.UserRegistrationInput;
import br.com.buscapetapi.buscapetapi.dto.output.UserOutput;
import br.com.buscapetapi.buscapetapi.dto.output.UserRegistrationOutput;
import br.com.buscapetapi.buscapetapi.model.User;
import br.com.buscapetapi.buscapetapi.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.modelmbean.ModelMBean;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService,
                          ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/new-account")
    public ResponseEntity<UserRegistrationOutput> createUser (@Valid @RequestBody UserRegistrationInput userInput){
        User createdUser = userService.createUser(userInput);
        UserRegistrationOutput user = modelMapper.map(createdUser, UserRegistrationOutput.class);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update-user")
    public ResponseEntity<UserOutput> updateUser(@Valid @RequestBody UserInput userInput){
        UserOutput updatedUser = userService.updateUser(userInput);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        boolean isAuthenticated = userService.authenticate(email, password);
        if (isAuthenticated) {
            String token = userService.generateToken(email);
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha incorretos");
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        userService.forgotPassword(email);
        return ResponseEntity.ok("Email enviado com sucesso.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam("token") String token, @RequestBody Map<String, String> request) {
        String newPassword = request.get("password");
        Optional<User> user = userService.findByResetToken(token);
        if (user.isPresent()) {
            userService.updatePassword(user.get(), newPassword);
            return ResponseEntity.ok("Senha alterada com sucesso.");
        } else {
            return ResponseEntity.badRequest().body("Token inválido.");
        }
    }


}
