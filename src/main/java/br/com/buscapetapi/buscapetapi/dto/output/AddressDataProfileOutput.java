package br.com.buscapetapi.buscapetapi.dto.output;

import lombok.Data;

@Data
public class AddressDataProfileOutput {
    private String street;
    private int number;
    private String neighborhood;
    private String cep;
    private String referencia;
    private String complemento;
}
