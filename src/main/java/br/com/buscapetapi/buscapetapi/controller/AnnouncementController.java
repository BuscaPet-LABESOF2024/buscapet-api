package br.com.buscapetapi.buscapetapi.controller;

import br.com.buscapetapi.buscapetapi.model.Announcement;
import br.com.buscapetapi.buscapetapi.service.AnnouncementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/announcements")
public class AnnouncementController {
    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @PostMapping("/new-announcement")
    public ResponseEntity<Announcement> createAnnouncement(@Valid @RequestBody Announcement announcementInput) {
        Announcement createdAnnouncement = announcementService.createAnnouncement(announcementInput);
        return ResponseEntity.ok(createdAnnouncement);
    }

    @PutMapping("/update-announcement")
    public ResponseEntity<Announcement> updateAnnouncement(@Valid @RequestBody Announcement announcementInput) {
        Announcement updatedAnnouncement = announcementService.updateAnnouncement(announcementInput);
        return ResponseEntity.ok(updatedAnnouncement);
    }

    @GetMapping("announcements/{id}")
    public ResponseEntity<Announcement> getAnnouncement(@PathVariable Long id) {
        Announcement announcement = announcementService.findById(id);
        return ResponseEntity.ok(announcement);
    }
}
