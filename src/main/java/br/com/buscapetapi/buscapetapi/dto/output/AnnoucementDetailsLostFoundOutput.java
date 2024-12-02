package br.com.buscapetapi.buscapetapi.dto.output;

import lombok.Data;

@Data
public class AnnoucementDetailsLostFoundOutput {
    private String title;
    private String description;
    private String contactPhone;
    private String contactEmail;
    private String images;
    private Long announcementType;
    private boolean active;
    private AnnoucementDetailsAddressOutput address;
    private AnnoucementDetailsAnimalLostFoundOutput animal;
}
