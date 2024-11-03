package br.com.buscapetapi.buscapetapi.dto.output;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserOutput {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private Long addressId;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
