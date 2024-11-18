package br.com.buscapetapi.buscapetapi.controller;

import br.com.buscapetapi.buscapetapi.dto.input.UserInput;
import br.com.buscapetapi.buscapetapi.dto.input.UserRegistrationInput;
import br.com.buscapetapi.buscapetapi.dto.input.UserUpdateRequestInput;
import br.com.buscapetapi.buscapetapi.dto.output.UserDataProfileOutput;
import br.com.buscapetapi.buscapetapi.dto.output.UserOutput;
import br.com.buscapetapi.buscapetapi.dto.output.UserRegistrationOutput;
import br.com.buscapetapi.buscapetapi.dto.output.UserUpdateRequestOutput;
import br.com.buscapetapi.buscapetapi.model.User;
import br.com.buscapetapi.buscapetapi.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/update-user")
    public ResponseEntity<UserUpdateRequestOutput> updateUser(HttpServletRequest request, @Valid @RequestBody UserUpdateRequestInput userUpdateRequestInput) {
        Long userId = (Long) request.getAttribute("userId");  // Extraído do token JWT

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Se não houver userId no request, retorna 401
        }

        UserUpdateRequestOutput updatedUser = userService.updateUser(userUpdateRequestInput,userId);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){

        User user = userService.findById(id);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Retorna 404 caso não encontre o usuário
        }

        return ResponseEntity.ok(user);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDataProfileOutput> getUserProfile(HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("userId");  // Extraído do token JWT

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Se não houver userId no request, retorna 401
        }

        UserDataProfileOutput profile = userService.getUserDataProfile(userId);

        if (profile == null) {
            return ResponseEntity.notFound().build(); // Retorna 404 caso o perfil não seja encontrado
        }
        return ResponseEntity.ok(profile);
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
