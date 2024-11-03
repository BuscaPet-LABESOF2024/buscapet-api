package br.com.buscapetapi.buscapetapi.service;

import br.com.buscapetapi.buscapetapi.dto.input.AdoptionAnnouncementInput;
import br.com.buscapetapi.buscapetapi.dto.input.AnimalInput;
import br.com.buscapetapi.buscapetapi.dto.output.AdoptionAnnouncementOutput;
import br.com.buscapetapi.buscapetapi.dto.output.AnnouncementOutput;
import br.com.buscapetapi.buscapetapi.dto.output.ImageAnnouncementOutput;
import br.com.buscapetapi.buscapetapi.model.Animal;
import br.com.buscapetapi.buscapetapi.model.Announcement;
import br.com.buscapetapi.buscapetapi.model.AnnouncementType;
import br.com.buscapetapi.buscapetapi.model.User;
import br.com.buscapetapi.buscapetapi.repository.AnnouncementRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final ModelMapper modelMapper;
    private final AnimalService animalService;
    private final UserService userService;
    private final AnnoucementTypeService annoucementTypeService;


    public AnnouncementService(AnnouncementRepository announcementRepository,
                               ModelMapper modelMapper,
                               AnimalService animalService,
                               UserService userService,
                               AnnoucementTypeService annoucementTypeService) {
        this.announcementRepository = announcementRepository;
        this.modelMapper = modelMapper;
        this.animalService = animalService;
        this.userService = userService;
        this.annoucementTypeService = annoucementTypeService;
    }

    public Announcement createAnnouncement(Announcement announcementInput) {
        announcementInput.setCreatedAt(LocalDateTime.now());
        announcementInput.setUpdatedAt(LocalDateTime.now());
        return announcementRepository.save(announcementInput);
    }

    public AdoptionAnnouncementOutput createAdoptionAnnouncement(AdoptionAnnouncementInput announcementInput) {
        // Criação do animal e busca pelo tipo de anúncio
        Animal newAnimal = animalService.createAnimal(announcementInput.getAnimal());
        AnnouncementType announcementType = annoucementTypeService.findById(announcementInput.getAnnouncementType().getId());
        User user = userService.findById(announcementInput.getUserId());

        // Ajusta os dados do input para o anúncio
        announcementInput.setAnimal(modelMapper.map(newAnimal, AnimalInput.class));
        announcementInput.setAnnouncementType(announcementType);

        Announcement announcement = modelMapper.map(announcementInput, Announcement.class);
        announcement.setContactEmail(user.getEmail());
        announcement.setCreatedAt(LocalDateTime.now());
        announcement.setUpdatedAt(LocalDateTime.now());

        Announcement createdAnnouncement = announcementRepository.save(announcement);

        // Mapeia para o formato de saída
        AdoptionAnnouncementOutput adoptionOutput = new AdoptionAnnouncementOutput();
        adoptionOutput.setTitle(createdAnnouncement.getTitle());
        adoptionOutput.setDescription(createdAnnouncement.getDescription());
        adoptionOutput.setAnimal(createdAnnouncement.getAnimal().getId());
        adoptionOutput.setAnnouncementType(createdAnnouncement.getAnnouncementType().getId());
        adoptionOutput.setContactPhone(createdAnnouncement.getContactPhone());
        adoptionOutput.setUser(createdAnnouncement.getUser().getId());
        adoptionOutput.setActive(true);

        return adoptionOutput;
    }


    public Announcement findById(Long id) {
        Optional<Announcement> existingAnnouncement = announcementRepository.findById(id);

        if (existingAnnouncement.isPresent()) {
            return existingAnnouncement.get();
        }
        return null;
    }

    //BPET-49 tela listar os animais
    public List<Announcement> findAll() {
        return announcementRepository.findAll(); // Chama o repositório para buscar todos os anúncios
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

    public List<AnnouncementOutput> findAllAnnouncementsWithImages() {
        List<Announcement> announcements = announcementRepository.findAll(); // Pega todos os anúncios
        return announcements.stream()
                .map(this::convertToAnnouncementOutput) // Converte cada Announcement para AnnouncementOutput
                .collect(Collectors.toList());
    }

    private AnnouncementOutput convertToAnnouncementOutput(Announcement announcement) {
        AnnouncementOutput output = new AnnouncementOutput();
        output.setId(announcement.getId());
        output.setTitle(announcement.getTitle());
        output.setDescription(announcement.getDescription());
        output.setContactPhone(announcement.getContactPhone());
        output.setContactEmail(announcement.getContactEmail());
        output.setCreatedAt(announcement.getCreatedAt());
        output.setUpdatedAt(announcement.getUpdatedAt());
        output.setImages(announcement.getImages().stream()
                .map(image -> new ImageAnnouncementOutput(image.getId(), image.getImage()))
                .collect(Collectors.toList()));
        return output;
    }

}
