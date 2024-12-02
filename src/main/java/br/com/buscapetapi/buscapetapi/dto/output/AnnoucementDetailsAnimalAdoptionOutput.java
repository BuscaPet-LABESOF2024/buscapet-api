package br.com.buscapetapi.buscapetapi.dto.output;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AnnoucementDetailsAnimalAdoptionOutput {
    private String name;
    private String type;
    private String breed;
    //private String gender;
    private Integer size;
    private BigDecimal weight;
    private Integer age;
}
