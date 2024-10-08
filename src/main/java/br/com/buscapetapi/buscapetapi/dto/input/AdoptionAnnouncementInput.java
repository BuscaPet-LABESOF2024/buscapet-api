package br.com.buscapetapi.buscapetapi.dto.input;

import br.com.buscapetapi.buscapetapi.model.Animal;
import br.com.buscapetapi.buscapetapi.model.AnnouncementType;
import br.com.buscapetapi.buscapetapi.model.ImageAnnouncement;
import lombok.Data;

@Data
public class AdoptionAnnouncementInput {
    private String title;
    private String description;
    private Animal animal;
    private AnnouncementType announcementType;
    private String contactPhone;
    private String contactEmail;
    private ImageAnnouncement imageAnnouncement;

}
