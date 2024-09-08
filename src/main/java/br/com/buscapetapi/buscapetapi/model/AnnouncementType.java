package br.com.buscapetapi.buscapetapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "announcementType")
public class AnnouncementType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "announcementType_id_sequence")
    @SequenceGenerator(sequenceName = "announcementType_id_sequence", allocationSize = 1, name = "announcementType_id_sequence")
    private int id;
    private String description;
}
