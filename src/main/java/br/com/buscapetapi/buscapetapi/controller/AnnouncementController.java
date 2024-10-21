package br.com.buscapetapi.buscapetapi.controller;

import br.com.buscapetapi.buscapetapi.dto.input.AdoptionAnnouncementInput;
import br.com.buscapetapi.buscapetapi.dto.output.AdoptionAnnouncementOutput;
import br.com.buscapetapi.buscapetapi.dto.output.AnnouncementOutput;
import br.com.buscapetapi.buscapetapi.dto.output.ImageAnnouncementOutput;
import br.com.buscapetapi.buscapetapi.model.Animal;
import br.com.buscapetapi.buscapetapi.model.Announcement;
import br.com.buscapetapi.buscapetapi.model.AnnouncementType;
import br.com.buscapetapi.buscapetapi.model.User;
import br.com.buscapetapi.buscapetapi.service.AnimalService;
import br.com.buscapetapi.buscapetapi.service.AnnoucementTypeService;
import br.com.buscapetapi.buscapetapi.service.AnnouncementService;
import br.com.buscapetapi.buscapetapi.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/announcement")
public class AnnouncementController {
    private final AnnouncementService announcementService;
    private final AnimalService animalService;
    private final UserService userService;
    private final AnnoucementTypeService annoucementTypeService;
    private final ModelMapper modelMapper;

    public AnnouncementController(AnnouncementService announcementService,
                                  AnimalService animalService,
                                  UserService userService,
                                  AnnoucementTypeService annoucementTypeService,
                                  ModelMapper modelMapper) {
        this.announcementService = announcementService;
        this.animalService = animalService;
        this.userService = userService;
        this.annoucementTypeService = annoucementTypeService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/new-announcement")
    public ResponseEntity<Announcement> createAnnouncement(@Valid @RequestBody Announcement announcementInput) {
        Announcement createdAnnouncement = announcementService.createAnnouncement(announcementInput);
        return ResponseEntity.ok(createdAnnouncement);
    }

    @PostMapping("new-adoption-announcement")
    public ResponseEntity<?> createAdoptionAnnouncement(@Valid @RequestBody AdoptionAnnouncementInput announcementInput){
        Animal newAnimal = animalService.createAnimal(announcementInput.getAnimal());
        User user = userService.findById(announcementInput.getUser().getId());
        AnnouncementType announcementType = annoucementTypeService.findById(announcementInput.getAnnouncementType().getId());

        announcementInput.setAnimal(newAnimal);
        announcementInput.setUser(user);
        announcementInput.setAnnouncementType(announcementType);

        Announcement createdAdoptionAnnouncement = announcementService.createAdoptionAnnoucement(announcementInput);
        AdoptionAnnouncementOutput adoption = modelMapper.map(createdAdoptionAnnouncement, AdoptionAnnouncementOutput.class);
        return ResponseEntity.ok(adoption);
    }

    @PutMapping("/update-announcement")
    public ResponseEntity<Announcement> updateAnnouncement(@Valid @RequestBody Announcement announcementInput) {
        Announcement updatedAnnouncement = announcementService.updateAnnouncement(announcementInput);
        return ResponseEntity.ok(updatedAnnouncement);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementOutput> getAnnouncement(@PathVariable Long id) {
        Announcement announcement = announcementService.findById(id);

        // Mapeando o anúncio para o DTO
        AnnouncementOutput announcementDTO = modelMapper.map(announcement, AnnouncementOutput.class);

        // Mapeando a lista de imagens, caso tenha
        List<ImageAnnouncementOutput> imageDTOs = announcement.getImages().stream()
                .map(image -> modelMapper.map(image, ImageAnnouncementOutput.class))
                .collect(Collectors.toList());

        announcementDTO.setImages(imageDTOs); // Definindo a lista de imagens no DTO

        return ResponseEntity.ok(announcementDTO);
    }

    //BPET-49 listar os animais
    @GetMapping("/all-announcement") // Novo endpoint para obter todos os anúncios
    public ResponseEntity<List<AnnouncementOutput>> getAllAnnouncements() {
        List<AnnouncementOutput> announcements = announcementService.findAllAnnouncementsWithImages(); // Chama o novo serviço
        return ResponseEntity.ok(announcements);
    }

}
