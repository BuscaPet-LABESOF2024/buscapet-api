package br.com.buscapetapi.buscapetapi.dto.input;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SearchInput {
    private Long announcementType;
    private Integer animalSize;
}
