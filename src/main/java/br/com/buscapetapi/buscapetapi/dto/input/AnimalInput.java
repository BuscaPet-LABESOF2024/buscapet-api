package br.com.buscapetapi.buscapetapi.dto.input;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AnimalInput {
    private Long id;
    private String name;
    private int statusAnimal;
    private String type;
    private String breed;
    private Integer size;
    private BigDecimal weight;
    private Integer age;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
