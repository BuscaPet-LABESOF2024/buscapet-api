package br.com.buscapetapi.buscapetapi.dto.output;

import lombok.Data;

import java.util.List;

@Data
public class AnnoucementDetailsAdoptionOutput {
    private Long id;
    private String title;
    private String description;
    private String contactPhone;
    private String contactEmail;
    private List<ImageAnnouncementOutput> images;
    private AnnoucementDetailsAnimalAdoptionOutput animal;
    private Long announcementType;
    private boolean active;
    private AnnoucementDetailsAddressOutput address;
}
