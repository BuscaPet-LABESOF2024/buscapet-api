package br.com.buscapetapi.buscapetapi.dto.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginUserInput {

    @NotNull
    @Email
    private String email;

    @NotNull
    @NotEmpty
    private String password;
}
