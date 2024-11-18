package br.com.buscapetapi.buscapetapi.dto.output;

import lombok.Data;

@Data
public class UserUpdateRequestOutput {
    private String name;
    private String phone;
}
