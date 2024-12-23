package br.com.buscapetapi.buscapetapi.controller;

import br.com.buscapetapi.buscapetapi.dto.input.LoginUserInput;
import br.com.buscapetapi.buscapetapi.dto.input.UserRegistrationInput;
import br.com.buscapetapi.buscapetapi.dto.output.LoginOutput;
import br.com.buscapetapi.buscapetapi.model.User;
import br.com.buscapetapi.buscapetapi.service.AuthenticationService;
import br.com.buscapetapi.buscapetapi.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody UserRegistrationInput registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginOutput> authenticate(@RequestBody LoginUserInput loginUserInput) {
        User authenticatedUser = authenticationService.authenticate(loginUserInput);

        String jwtToken = jwtService.generateToken(authenticatedUser, authenticatedUser.getId());

        LoginOutput loginResponse = new LoginOutput();

        loginResponse.setUsername(authenticatedUser.getName());
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
