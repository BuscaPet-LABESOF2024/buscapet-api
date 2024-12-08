package br.com.buscapetapi.buscapetapi.dto.input;

import br.com.buscapetapi.buscapetapi.model.AnnouncementType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AnnouncementUpdateInput {
    private Long id;
    private String title;
    private String description;
    private LocalDate data;
    private AddressInput address;
    private AnimalInput animal;
    private AnnouncementType announcementType;
    private String contactPhone;
    private Long userId;
    private ImageAnnouncementInput imageAnnouncement;
}
