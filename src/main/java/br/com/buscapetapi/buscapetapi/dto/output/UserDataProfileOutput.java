package br.com.buscapetapi.buscapetapi.dto.output;

import lombok.Data;

@Data
public class UserDataProfileOutput {
    private String name;
    private String email;
    private String phone;
    AddressDataProfileOutput address;
}
