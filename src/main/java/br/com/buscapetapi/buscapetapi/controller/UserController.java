package br.com.buscapetapi.buscapetapi.controller;

import br.com.buscapetapi.buscapetapi.model.User;
import br.com.buscapetapi.buscapetapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.modelmbean.ModelMBean;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/new-account")
    public ResponseEntity<User> createUser (@Valid @RequestBody User userInput){
        User createdUser = userService.createUser(userInput);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User userInput){
        User updatedUser = userService.updateUser(userInput);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }
}
