package br.com.buscapetapi.buscapetapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "announcement_type")
public class AnnouncementType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //Coloquei como Long pro JPARepository funcionar
    private String description;
}
