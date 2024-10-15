package br.com.buscapetapi.buscapetapi.dto.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationInput {

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 8, max = 100)
    private String password;
}
