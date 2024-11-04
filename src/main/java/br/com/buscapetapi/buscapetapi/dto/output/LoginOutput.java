package br.com.buscapetapi.buscapetapi.dto.output;

import lombok.Data;

@Data
public class LoginOutput {
    private String username;
    private String token;
    private long expiresIn;
}
