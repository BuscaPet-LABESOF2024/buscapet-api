package br.com.buscapetapi.buscapetapi.controller;

import br.com.buscapetapi.buscapetapi.dto.input.ImageAnnouncementInput;
import br.com.buscapetapi.buscapetapi.model.ImageAnnouncement;
import br.com.buscapetapi.buscapetapi.service.ImageAnnouncementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("image-announcement")
public class ImageAnnouncementController {

    private final ImageAnnouncementService imageAnnouncementService;

    public ImageAnnouncementController(ImageAnnouncementService imageAnnouncementService) {
        this.imageAnnouncementService = imageAnnouncementService;
    }

    @PostMapping("/new-image-announcement")
    public ResponseEntity<ImageAnnouncement> createImageAnnouncement(@Valid @RequestBody ImageAnnouncementInput imageAnnouncementInput, Long announcementId) {
        ImageAnnouncement createdImageAnnouncement = imageAnnouncementService.createImageAnnouncement(imageAnnouncementInput, announcementId);
        return ResponseEntity.ok(createdImageAnnouncement);
    }

    @PutMapping("/update-image-announcement")
    public ResponseEntity<ImageAnnouncement> updateImageAnnouncement(@Valid @RequestBody ImageAnnouncement imageAnnouncementInput) {
        ImageAnnouncement updatedImageAnnouncement = imageAnnouncementService.updateImageAnnouncement(imageAnnouncementInput);
        return ResponseEntity.ok(updatedImageAnnouncement);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageAnnouncement> getImageAnnouncement(@PathVariable Long id) {
        ImageAnnouncement imageAnnouncement = imageAnnouncementService.findById(id);
        return ResponseEntity.ok(imageAnnouncement);
    }
}
