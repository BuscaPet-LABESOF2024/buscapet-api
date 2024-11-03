package br.com.buscapetapi.buscapetapi.dto.output;

import lombok.Data;

@Data
public class UserRegistrationOutput {

    private String name;
    private String email;
    private String password;
}
