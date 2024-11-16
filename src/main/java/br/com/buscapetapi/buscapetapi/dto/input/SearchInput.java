package br.com.buscapetapi.buscapetapi.dto.input;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SearchInput {
    private Long announcementType;
    private String animalType;
    private String animalBreed;
    private Integer animalSize;
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private String neighborhood;
}
