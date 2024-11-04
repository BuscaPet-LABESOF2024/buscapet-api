package br.com.buscapetapi.buscapetapi.dto.input;
import br.com.buscapetapi.buscapetapi.model.AnnouncementType;
import lombok.Data;

@Data
public class AdoptionAnnouncementInput {
    private String title;
    private String description;
    private AnimalInput animal;
    private AnnouncementType announcementType;
    private String contactPhone;
    private Long userId;
    private ImageAnnouncementInput imageAnnouncement;

}
