package br.com.buscapetapi.buscapetapi.dto.input;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserInput {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private Long addressId;
    private String street;
    private Integer number;
    private String neighborhood;
    private String cep;
    private LocalDateTime addressUpdatedAt;
    private String referencia;
    private String complemento;
    private LocalDate createdAt;
    private LocalDate updatedAt;

}
