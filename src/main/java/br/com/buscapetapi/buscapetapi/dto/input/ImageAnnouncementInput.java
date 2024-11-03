package br.com.buscapetapi.buscapetapi.dto.input;

import br.com.buscapetapi.buscapetapi.model.Announcement;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ImageAnnouncementInput {
    private Long id;
    private String image;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private Long announcementId;
}
