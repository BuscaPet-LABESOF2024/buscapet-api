package br.com.buscapetapi.buscapetapi.service;

import br.com.buscapetapi.buscapetapi.model.ImageAnnouncement;
import br.com.buscapetapi.buscapetapi.repository.ImageAnnouncementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ImageAnnouncementService {

    private final ImageAnnouncementRepository imageAnnouncementRepository;

    public ImageAnnouncementService(ImageAnnouncementRepository imageAnnouncementRepository) {
        this.imageAnnouncementRepository = imageAnnouncementRepository;
    }

    public ImageAnnouncement createImageAnnouncement(ImageAnnouncement imageAnnouncementInput) {
        imageAnnouncementInput.setCreatedAt(LocalDate.now());
        imageAnnouncementInput.setUpdatedAt(LocalDate.now());
        return imageAnnouncementRepository.save(imageAnnouncementInput);
    }

    public ImageAnnouncement findById(Long id) {
        Optional<ImageAnnouncement> existingImageAnnouncement = imageAnnouncementRepository.findById(id);

        if (existingImageAnnouncement.isPresent()) {
            return existingImageAnnouncement.get();
        }
        return null;  // ou você pode lançar uma exceção personalizada aqui
    }

    public ImageAnnouncement updateImageAnnouncement(ImageAnnouncement imageAnnouncementInput) {
        Optional<ImageAnnouncement> existingImageAnnouncement = imageAnnouncementRepository.findById(imageAnnouncementInput.getId());

        if (existingImageAnnouncement.isPresent()) {
            ImageAnnouncement imageAnnouncement = existingImageAnnouncement.get();

            imageAnnouncement.setImage(imageAnnouncementInput.getImage());
            imageAnnouncement.setAnnouncement(imageAnnouncementInput.getAnnouncement());
            imageAnnouncement.setCreatedAt(imageAnnouncementInput.getCreatedAt());
            imageAnnouncement.setUpdatedAt(LocalDate.now());

            return imageAnnouncementRepository.save(imageAnnouncement);
        }
        return null;
    }
}
