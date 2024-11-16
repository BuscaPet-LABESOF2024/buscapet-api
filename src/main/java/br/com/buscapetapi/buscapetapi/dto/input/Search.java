package br.com.buscapetapi.buscapetapi.dto.input;

import br.com.buscapetapi.buscapetapi.model.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Search {
    private Long announcementType;
    private String animalType;
    private String animalBreed;
    private Integer animalSize;
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private String neighborhood;
}
