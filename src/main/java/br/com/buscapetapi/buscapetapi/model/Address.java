package br.com.buscapetapi.buscapetapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150, nullable = false)
    private String street;

    @Column(nullable = false)
    private int number;

    @Column(length = 100, nullable = false)
    private String neighborhood;

    @Column(length = 11)
    private String cep;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(length = 150)
    private String referencia;

    @Column(length = 150)
    private String complemento;
}
