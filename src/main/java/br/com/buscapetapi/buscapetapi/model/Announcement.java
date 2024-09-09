package br.com.buscapetapi.buscapetapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "announcement")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "announcement_id_sequence")
    @SequenceGenerator(sequenceName = "announcement_id_sequence", allocationSize = 1, name = "announcement_id_sequence")
    private Long id;

    @Size(max = 50)
    @NotNull
    @Column(name = "title", length = 50)
    private String title;

    @Column(name = "description")
    private String description;

    @Size(max = 15)
    @NotNull
    @Column(name = "contact_phone", length = 15)
    private String contactPhone;

    @Size(max = 100)
    @Column(name = "contact_email", length = 100)
    private String contactEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "announcement_type_id")
    private AnnouncementType announcementType;

    @NotNull
    @Column(name = "active")
    private boolean active;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
