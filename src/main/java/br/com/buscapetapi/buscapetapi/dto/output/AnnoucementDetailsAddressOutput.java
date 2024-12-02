package br.com.buscapetapi.buscapetapi.dto.output;

import lombok.Data;

@Data
public class AnnoucementDetailsAddressOutput {
    private String street;
    private String neighborhood;
    private String reference;
    private String complemento;
}
