package br.com.buscapetapi.buscapetapi.service;

import br.com.buscapetapi.buscapetapi.model.Announcement;
import br.com.buscapetapi.buscapetapi.model.AnnouncementType;
import br.com.buscapetapi.buscapetapi.repository.AnnouncementTypeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnnoucementTypeService {
    private final AnnouncementTypeRepository announcementTypeRepository;

    public AnnoucementTypeService(AnnouncementTypeRepository announcementTypeRepository){
        this.announcementTypeRepository = announcementTypeRepository;
    }

    public AnnouncementType findById(Long id){
        Optional<AnnouncementType> existingAnnoucementType = announcementTypeRepository.findById(id);

        if (existingAnnoucementType.isPresent()){
            return existingAnnoucementType.get();
        }
        return null;
    }
}
