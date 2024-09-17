package br.com.buscapetapi.buscapetapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "animal")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50)
    @NotNull
    @Column(name = "name", length = 50)
    private String name;

    @NotNull
    @Column(name = "status_animal")
    private int statusAnimal;

    @Size(max = 50)
    @NotNull
    @Column(name = "type", length = 50)
    private String type;

    @Size(max = 50)
    @NotNull
    @Column(name = "breed", length = 50)
    private String breed;

    @NotNull
    @Column(name = "size_animal", length = 10)
    private Integer size;

    @NotNull
    @Column(name = "weight", precision = 5, scale = 2)
    private BigDecimal weight;

    @NotNull
    @Column(name = "age")
    private Integer age;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
