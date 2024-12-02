package br.com.buscapetapi.buscapetapi.dto.output;

import lombok.Data;

@Data
public class AnnoucementDetailsAnimalLostFoundOutput {
    private String name;
    private String type;
    private String breed;
    private Integer size;
}
