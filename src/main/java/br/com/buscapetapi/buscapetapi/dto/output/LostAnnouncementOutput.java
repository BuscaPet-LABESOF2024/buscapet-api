package br.com.buscapetapi.buscapetapi.dto.output;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LostAnnouncementOutput {
    private String title;
    private String description;
    private LocalDate data;
    private Long address;
    private Long animal;
    private Long announcementType;
    private String contactPhone;
    private Long user;
    private boolean active;
}
