package br.com.buscapetapi.buscapetapi.dto.input;

import lombok.Data;

@Data
public class AddressUpdateRequestInput {
    private String street;
    private Integer number;
    private String neighborhood;
    private String cep;
    private String referencia;
    private String complemento;
}
