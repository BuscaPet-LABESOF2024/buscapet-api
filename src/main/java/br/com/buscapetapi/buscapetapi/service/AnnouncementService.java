package br.com.buscapetapi.buscapetapi.service;

import br.com.buscapetapi.buscapetapi.dto.input.AdoptionAnnouncementInput;
import br.com.buscapetapi.buscapetapi.model.Announcement;
import br.com.buscapetapi.buscapetapi.repository.AnnouncementRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final ModelMapper modelMapper;


    public AnnouncementService(AnnouncementRepository announcementRepository, ModelMapper modelMapper) {
        this.announcementRepository = announcementRepository;
        this.modelMapper = modelMapper;
    }

    public Announcement createAnnouncement(Announcement announcementInput) {
        announcementInput.setCreatedAt(LocalDateTime.now());
        announcementInput.setUpdatedAt(LocalDateTime.now());
        return announcementRepository.save(announcementInput);
    }

    public Announcement createAdoptionAnnoucement(AdoptionAnnouncementInput announcementInput){
        Announcement announcement = modelMapper.map(announcementInput, Announcement.class);
        announcement.setContactEmail(announcementInput.getUser().getEmail());
        announcement.setCreatedAt(LocalDateTime.now());
        announcement.setUpdatedAt(LocalDateTime.now());
        return announcementRepository.save(announcement);
    }

    public Announcement findById(Long id) {
        Optional<Announcement> existingAnnouncement = announcementRepository.findById(id);

        if (existingAnnouncement.isPresent()) {
            return existingAnnouncement.get();
        }
        return null;
    }

    //editar um anúncio task BPET-38
    public Announcement updateAnnouncement(Announcement announcementInput) {
        Optional<Announcement> existingAnnouncement = announcementRepository.findById(announcementInput.getId());

        if (existingAnnouncement.isPresent()) {
            Announcement announcement = existingAnnouncement.get();
            announcement.setTitle(announcementInput.getTitle());
            announcement.setDescription(announcementInput.getDescription());
            announcement.setContactEmail(announcementInput.getContactEmail());
            announcement.setContactPhone(announcementInput.getContactPhone());
            announcement.setUpdatedAt(LocalDateTime.now());
            return announcementRepository.save(announcement);
        }
        return null;
    }
}
