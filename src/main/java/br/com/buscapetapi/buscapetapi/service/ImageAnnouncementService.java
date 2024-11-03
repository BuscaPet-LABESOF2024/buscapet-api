package br.com.buscapetapi.buscapetapi.service;

import br.com.buscapetapi.buscapetapi.dto.input.ImageAnnouncementInput;
import br.com.buscapetapi.buscapetapi.model.ImageAnnouncement;
import br.com.buscapetapi.buscapetapi.repository.ImageAnnouncementRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ImageAnnouncementService {

    private final ImageAnnouncementRepository imageAnnouncementRepository;
    private final ModelMapper modelMapper;

    public ImageAnnouncementService(ImageAnnouncementRepository imageAnnouncementRepository,
                                    ModelMapper modelMapper) {
        this.imageAnnouncementRepository = imageAnnouncementRepository;
        this.modelMapper = modelMapper;
    }

    public ImageAnnouncement createImageAnnouncement(ImageAnnouncementInput imageAnnouncementInput, Long announcementId) {
        imageAnnouncementInput.setCreatedAt(LocalDate.now());
        imageAnnouncementInput.setUpdatedAt(LocalDate.now());
        imageAnnouncementInput.setAnnouncementId(announcementId);

        ImageAnnouncement image = modelMapper.map(imageAnnouncementInput, ImageAnnouncement.class);
        return imageAnnouncementRepository.save(image);
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
