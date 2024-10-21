package br.com.buscapetapi.buscapetapi.dto.output;

import br.com.buscapetapi.buscapetapi.model.Animal;
import br.com.buscapetapi.buscapetapi.model.AnnouncementType;
import br.com.buscapetapi.buscapetapi.model.ImageAnnouncement;
import br.com.buscapetapi.buscapetapi.model.User;
import lombok.Data;

@Data
public class AdoptionAnnouncementOutput {
    private String title;
    private String description;
    private Long animal;
    private Long announcementType;
    private String contactPhone;
    private Long user;
    private boolean active;
}
