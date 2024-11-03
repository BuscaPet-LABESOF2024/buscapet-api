package br.com.buscapetapi.buscapetapi.dto.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data // Adicione esta anotação para gerar getters e setters automaticamente
@NoArgsConstructor // Gera um construtor sem argumentos
@AllArgsConstructor // Gera um construtor que aceita todos os campos
public class ImageAnnouncementOutput {
    private Long id;
    private String image;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private Long announcementId;
}
