package br.com.buscapetapi.buscapetapi.dto.output;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AnnoucementDetailsLostFoundOutput {
    private Long id;
    private String title;
    private String description;
    private String contactPhone;
    private String contactEmail;
    private List<ImageAnnouncementOutput> images;
    private Long announcementType;
    private boolean active;
    private LocalDate data;
    private AnnoucementDetailsAddressOutput address;
    private AnnoucementDetailsAnimalLostFoundOutput animal;
}
