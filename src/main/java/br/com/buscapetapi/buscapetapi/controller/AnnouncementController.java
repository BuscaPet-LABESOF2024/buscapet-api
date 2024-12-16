package br.com.buscapetapi.buscapetapi.controller;

import br.com.buscapetapi.buscapetapi.config.UserCredentials;
import br.com.buscapetapi.buscapetapi.dto.input.*;
import br.com.buscapetapi.buscapetapi.dto.output.*;
import br.com.buscapetapi.buscapetapi.model.Announcement;
import br.com.buscapetapi.buscapetapi.model.AnnouncementType;
import br.com.buscapetapi.buscapetapi.service.AnnouncementService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/announcement")
public class AnnouncementController {
    private final AnnouncementService announcementService;
    private final ModelMapper modelMapper;

    public AnnouncementController(AnnouncementService announcementService,
                                  ModelMapper modelMapper) {
        this.announcementService = announcementService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/new-announcement")
    public ResponseEntity<Announcement> createAnnouncement(@Valid @RequestBody Announcement announcementInput) {
        Announcement createdAnnouncement = announcementService.createAnnouncement(announcementInput);
        return ResponseEntity.ok(createdAnnouncement);
    }

    @PostMapping("new-adoption-announcement")
    public ResponseEntity<AdoptionAnnouncementOutput> createAdoptionAnnouncement(
            HttpServletRequest request,
            @Valid @RequestBody AdoptionAnnouncementInput announcementInput) {

        //pegando id do token
        announcementInput.setUserId(UserCredentials.getUserId(request));

        // Chamando o serviço para criar o anúncio de adoção
        AdoptionAnnouncementOutput createdAdoption =
                announcementService.createAdoptionAnnouncement(announcementInput);

        return ResponseEntity.ok(createdAdoption);
    }

    @PostMapping("new-found-announcement")
    public ResponseEntity<FoundAnnouncementOutput> createFoundAnnouncement(
            HttpServletRequest request,
            @Valid @RequestBody FoundAnnouncementInput announcementInput) {

        //pegando id do token
        announcementInput.setUserId(UserCredentials.getUserId(request));

        // Chamando o serviço para criar o anúncio de adoção
        FoundAnnouncementOutput createdAnnouncement =
                announcementService.createFoundAnnouncement(announcementInput);

        return ResponseEntity.ok(createdAnnouncement);
    }

    @PostMapping("new-lost-announcement")
    public ResponseEntity<LostAnnouncementOutput> createLostAnnouncement(
            HttpServletRequest request,
            @Valid @RequestBody LostAnnouncementInput announcementInput) {
        //pegando id do token
        announcementInput.setUserId(UserCredentials.getUserId(request));

        FoundAnnouncementInput announcement = modelMapper.map(announcementInput, FoundAnnouncementInput.class);

        // Chamando o serviço para criar o anúncio de adoção
        FoundAnnouncementOutput createdAdoption =
                announcementService.createFoundAnnouncement(announcement);
        LostAnnouncementOutput output = modelMapper.map(createdAdoption, LostAnnouncementOutput.class);

        return ResponseEntity.ok(output);
    }

    @PutMapping("update-announcement")
    public ResponseEntity<AnnouncementUpdateOutput> updateAnnouncement(
            HttpServletRequest request,
            @Valid @RequestBody AnnouncementUpdateInput announcementInput) {
            System.out.println("Update Announcement");
            announcementInput.setUserId(UserCredentials.getUserId(request));
            // Chamando o serviço para atualizar o anúncio
            AnnouncementUpdateOutput updatedAnnouncement = announcementService.updateAnnouncement(announcementInput);
            return ResponseEntity.ok(updatedAnnouncement);
    }

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody SearchInput searchInput,
                                    @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                    @RequestParam(required = false, defaultValue = "10") Integer size) {
        Page<AnnouncementOutput> announcements = announcementService.findByFilters(searchInput, pageNumber, size);
        return  ResponseEntity.ok(announcements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> announcementDetails(@PathVariable Long id) {
        Announcement announcement = announcementService.findById(id);

        if (announcement == null) {
            return ResponseEntity.notFound().build();
        }

        Long announcementTypeId = announcement.getAnnouncementType().getId();
        System.out.println("Announcement Type ID: " + announcementTypeId);

        // Lógica específica para cada tipo de anúncio
        return switch (announcementTypeId.intValue()) {
            case 1 -> {
                AnnoucementDetailsLostFoundOutput lostOutput = announcementService.annoucementDetailsLostFound(id);
                yield ResponseEntity.ok(lostOutput); // DTO para perdido
            }
            case 2 -> {
                AnnoucementDetailsLostFoundOutput foundOutput = announcementService.annoucementDetailsLostFound(id);
                yield ResponseEntity.ok(foundOutput); // DTO para encontrado
            }
            case 3 -> {
                AnnoucementDetailsAdoptionOutput adoptionOutput = announcementService.annoucementDetailsAdoption(id);
                yield ResponseEntity.ok(adoptionOutput); // DTO para adoção
            }
            default -> ResponseEntity.badRequest().body("Tipo de anúncio desconhecido");
        };
    }

    @PutMapping("/deactivate-announcement/{id}")
    public ResponseEntity<Void> deactivateAnnouncement(@PathVariable Long id) {
        announcementService.deactivateAnnouncement(id);
        return ResponseEntity.noContent().build();
    }

    //BPET-49 listar os animais
    @GetMapping("/all-announcement") // Novo endpoint para obter todos os anúncios
    public ResponseEntity<List<AnnouncementOutput>> getAllAnnouncements() {
        List<AnnouncementOutput> announcements = announcementService.findAllAnnouncementsWithImages(); // Chama o novo serviço
        return ResponseEntity.ok(announcements);
    }

    @GetMapping("/types")
    public ResponseEntity<List<AnnouncementType>> getTypes() {
        List<AnnouncementType> types = announcementService.findTypes();

        return ResponseEntity.ok(types);
    }
    @GetMapping("/my-announcements")
    public ResponseEntity<List<AnnouncementOutput>> getMyAnnouncements(HttpServletRequest request) {
        Long userId = UserCredentials.getUserId(request);
        List<AnnouncementOutput> announcements = announcementService.findMyAnnouncements(userId);
        return ResponseEntity.ok(announcements);
    }

    @GetMapping("/last-announcements")
    public ResponseEntity<List<AnnouncementOutput>> getLastAnnouncements() {
        List<AnnouncementOutput> announcements = announcementService.findLastAnnouncementsWithImages();
        return ResponseEntity.ok(announcements);
    }

}
