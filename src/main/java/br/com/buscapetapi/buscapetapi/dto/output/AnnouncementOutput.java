package br.com.buscapetapi.buscapetapi.dto.output;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data // Adicione esta anotação para gerar getters e setters automaticamente
public class AnnouncementOutput {
    private Long id;
    private String title;
    private String description;
    private String contactPhone;
    private String contactEmail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ImageAnnouncementOutput> images; // Lista de imagens associadas
}
