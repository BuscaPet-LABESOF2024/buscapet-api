package br.com.buscapetapi.buscapetapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sequence")
    @SequenceGenerator(sequenceName = "id_sequence", allocationSize = 1, name = "id_sequence")
    private Long id;

    @Size(max = 50)
    @NotNull
    @Column(name = "name", length = 50)
    private String name;

    @Size(max = 150)
    @NotNull
    @Column(name = "email", length = 150)
    private String email;

    @Size(max = 15)
    @NotNull
    @Column(name = "phone", length = 15)
    private String phone;

    @Size(max = 150)
    @NotNull
    @Column(name = "password", length = 150)
    private String password;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private int address;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

}
