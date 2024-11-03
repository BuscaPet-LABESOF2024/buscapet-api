package br.com.buscapetapi.buscapetapi.dto.input;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddressInput {
    private Long id;
    private String street;
    private int number;
    private String neighborhood;
    private String cep;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String referencia;
    private String complemento;
}
