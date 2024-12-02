package br.com.buscapetapi.buscapetapi.service;

import br.com.buscapetapi.buscapetapi.dto.input.*;
import br.com.buscapetapi.buscapetapi.dto.output.*;
import br.com.buscapetapi.buscapetapi.model.*;
import br.com.buscapetapi.buscapetapi.repository.AnnouncementRepository;
import br.com.buscapetapi.buscapetapi.repository.AnnouncementTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.buscapetapi.buscapetapi.repository.AnnouncementSpecification.*;
import static br.com.buscapetapi.buscapetapi.repository.AnnouncementSpecification.byAnnouncementType;

@Service
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final AnnouncementTypeRepository announcementTypeRepository;
    private final ModelMapper modelMapper;
    private final AnimalService animalService;
    private final UserService userService;
    private final AnnoucementTypeService annoucementTypeService;
    private final ImageAnnouncementService imageAnnouncementService;
    private final AddressService addressService;

    public AnnouncementService(AnnouncementRepository announcementRepository,
                               AnnouncementTypeRepository announcementTypeRepository,
                               ModelMapper modelMapper,
                               AnimalService animalService,
                               UserService userService,
                               AnnoucementTypeService annoucementTypeService,
                               ImageAnnouncementService imageAnnouncementService,
                               AddressService addressService, JwtService jwtService) {
        this.announcementRepository = announcementRepository;
        this.announcementTypeRepository = announcementTypeRepository;
        this.modelMapper = modelMapper;
        this.animalService = animalService;
        this.userService = userService;
        this.annoucementTypeService = annoucementTypeService;
        this.imageAnnouncementService = imageAnnouncementService;
        this.addressService = addressService;
    }

    public Page<AnnouncementOutput> findByFilters(SearchInput searchInput, Integer pageNumber, Integer size) {
        Pageable page = PageRequest.of(pageNumber, size, Sort.by(Sort.Order.desc("id")));
        Page<Announcement> announcements = announcementRepository.findAll(
                byAnnouncementType(searchInput.getAnnouncementType())
                        .and(bySize(searchInput.getAnimalSize())),
                page
        );

        // Mapeando os resultados para AnnouncementOutput usando modelMapper
        return announcements.map(announcement -> modelMapper.map(announcement, AnnouncementOutput.class));
    }



    public Announcement createAnnouncement(Announcement announcementInput) {
        announcementInput.setCreatedAt(LocalDateTime.now());
        announcementInput.setUpdatedAt(LocalDateTime.now());
        return announcementRepository.save(announcementInput);
    }

    public AdoptionAnnouncementOutput createAdoptionAnnouncement(AdoptionAnnouncementInput announcementInput) {

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


        // Acrescenta o id do anuncio já criado a imagem
        ImageAnnouncement image = imageAnnouncementService.createImageAnnouncement(announcementInput.getImageAnnouncement(), createdAnnouncement.getId());

        return adoptionOutput;
    }

    public FoundAnnouncementOutput createFoundAnnouncement(FoundAnnouncementInput announcementInput) {

        // Cria ou atualiza o animal associado ao anúncio
        Animal newAnimal = animalService.createAnimal(announcementInput.getAnimal());
        AnnouncementType announcementType = annoucementTypeService.findById(announcementInput.getAnnouncementType().getId());
        User user = userService.findById(announcementInput.getUserId());
        Address newAddress = addressService.createAddress(announcementInput.getAddress());

        // Prepara o objeto de entrada para o anúncio
        announcementInput.setAnimal(modelMapper.map(newAnimal, AnimalInput.class));
        announcementInput.setAddress(modelMapper.map(newAddress, AddressInput.class));
        announcementInput.setAnnouncementType(announcementType);

        Announcement announcement = modelMapper.map(announcementInput, Announcement.class);
        announcement.setData(announcementInput.getData());
        announcement.setContactEmail(user.getEmail());
        announcement.setCreatedAt(LocalDateTime.now());
        announcement.setUpdatedAt(LocalDateTime.now());
        announcement.setUser(user);
        announcement.setActive(true);
        announcement.setId(null);

        // Salva o anúncio no repositório
        Announcement createdAnnouncement = announcementRepository.save(announcement);

        // Cria a imagem associada ao anúncio
        ImageAnnouncement image = imageAnnouncementService.createImageAnnouncement(announcementInput.getImageAnnouncement(), createdAnnouncement.getId());

        // Mapeia o objeto `Announcement` salvo para `FoundAnnouncementOutput`
        FoundAnnouncementOutput foundAnnouncementOutput = new FoundAnnouncementOutput();
        foundAnnouncementOutput.setTitle(createdAnnouncement.getTitle());
        foundAnnouncementOutput.setDescription(createdAnnouncement.getDescription());
        foundAnnouncementOutput.setData(createdAnnouncement.getData());
        foundAnnouncementOutput.setAddress(createdAnnouncement.getAddress().getId());
        foundAnnouncementOutput.setAnimal(createdAnnouncement.getAnimal().getId());
        foundAnnouncementOutput.setAnnouncementType(createdAnnouncement.getAnnouncementType().getId());
        foundAnnouncementOutput.setContactPhone(createdAnnouncement.getContactPhone());
        foundAnnouncementOutput.setUser(createdAnnouncement.getUser().getId());
        foundAnnouncementOutput.setActive(createdAnnouncement.isActive());

        return foundAnnouncementOutput;
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
        List<Announcement> announcements = announcementRepository.findAll(Sort.by(Sort.Order.desc("id"))); // Pega todos os anúncios
        return announcements.stream()
                .map(this::convertToAnnouncementOutput) // Converte cada Announcement para AnnouncementOutput
                .collect(Collectors.toList());
    }

    public List<AnnouncementOutput> findLastAnnouncementsWithImages() {
        List<Announcement> announcements = announcementRepository.findLastAnnouncements();
        return announcements.stream()
                .map(this::convertToAnnouncementOutput)
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
        //tá dando erro agora que a image é string
        if (announcement.getImages() != null) {
            // Mapeia as imagens para ImageAnnouncementOutput
            List<ImageAnnouncementOutput> imageOutputs = announcement.getImages().stream()
                    .map(image -> new ImageAnnouncementOutput(image.getId(), image.getImage()))
                    .collect(Collectors.toList());
            output.setImages(imageOutputs);
        } else {
            output.setImages(Collections.emptyList()); // Inicializa como lista vazia se nula
        }
        return output;
    }

    public List<AnnouncementType> findTypes() {
        return announcementTypeRepository.findAll();
    }

    public AnnoucementDetailsAdoptionOutput annoucementDetailsAdoption(Long id) {

        System.out.println("Entrou no método annoucementDetailsAdoption com o id do anúncio: " + id);

        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anúncio não encontrado"));

        // Inicializando o DTO
        AnnoucementDetailsAdoptionOutput annoucementDetailsAdoptionOutput = new AnnoucementDetailsAdoptionOutput();
        annoucementDetailsAdoptionOutput.setTitle(announcement.getTitle());
        annoucementDetailsAdoptionOutput.setDescription(announcement.getDescription());
        annoucementDetailsAdoptionOutput.setContactPhone(announcement.getContactPhone());
        annoucementDetailsAdoptionOutput.setContactEmail(announcement.getContactEmail());

        System.out.println("Dados do animal: " + announcement.getAnimal());

        // Verificando e mapeando o animal, se existir
        if (announcement.getAnimal() != null) {
            AnnoucementDetailsAnimalAdoptionOutput animalOutput = modelMapper.map(
                    announcement.getAnimal(), AnnoucementDetailsAnimalAdoptionOutput.class);
            annoucementDetailsAdoptionOutput.setAnimal(animalOutput);
        }

        // Verificando e mapeando o tipo de anúncio, se existir
        if (announcement.getAnnouncementType() != null) {
            annoucementDetailsAdoptionOutput.setAnnouncementType(announcement.getAnnouncementType().getId());
        }

        // Verificando e adicionando o status ativo
        annoucementDetailsAdoptionOutput.setActive(announcement.isActive());

        // Verificando e mapeando as imagens, se existir
        if (announcement.getImages() != null) {
            List<ImageAnnouncementOutput> imageOutputs = announcement.getImages().stream()
                    .map(image -> new ImageAnnouncementOutput(image.getId(), image.getImage()))
                    .collect(Collectors.toList());
            annoucementDetailsAdoptionOutput.setImages(imageOutputs.toString());
        }

        // Verificando e mapeando o endereço, se existir
        if (announcement.getAddress() != null) {
            AnnoucementDetailsAddressOutput addressOutput = modelMapper.map(
                    announcement.getAddress(), AnnoucementDetailsAddressOutput.class);
            annoucementDetailsAdoptionOutput.setAddress(addressOutput);
        }

        return annoucementDetailsAdoptionOutput;
    }

    public AnnoucementDetailsLostFoundOutput annoucementDetailsLostFound(Long id) {

        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anúncio não encontrado"));

        // Inicializando o DTO
        AnnoucementDetailsLostFoundOutput annoucementDetailsLostFoundOutput = new AnnoucementDetailsLostFoundOutput();
        annoucementDetailsLostFoundOutput.setTitle(announcement.getTitle());
        annoucementDetailsLostFoundOutput.setDescription(announcement.getDescription());
        annoucementDetailsLostFoundOutput.setContactPhone(announcement.getContactPhone());
        annoucementDetailsLostFoundOutput.setContactEmail(announcement.getContactEmail());

        // Verificando e mapeando o animal, se existir
        if (announcement.getAnimal() != null) {
            AnnoucementDetailsAnimalLostFoundOutput animalOutput = modelMapper.map(
                    announcement.getAnimal(), AnnoucementDetailsAnimalLostFoundOutput.class);
            annoucementDetailsLostFoundOutput.setAnimal(animalOutput);
        }

        // Verificando e mapeando o tipo de anúncio, se existir
        if (announcement.getAnnouncementType() != null) {
            annoucementDetailsLostFoundOutput.setAnnouncementType(announcement.getAnnouncementType().getId());
        }

        // Verificando e adicionando o status ativo
        annoucementDetailsLostFoundOutput.setActive(announcement.isActive());

        // Verificando e mapeando as imagens, se existir
        if (announcement.getImages() != null) {
            List<ImageAnnouncementOutput> imageOutputs = announcement.getImages().stream()
                    .map(image -> new ImageAnnouncementOutput(image.getId(), image.getImage()))
                    .collect(Collectors.toList());
            annoucementDetailsLostFoundOutput.setImages(imageOutputs.toString());
        }

        // Verificando e mapeando o endereço, se existir
        if (announcement.getAddress() != null) {
            AnnoucementDetailsAddressOutput addressOutput = modelMapper.map(
                    announcement.getAddress(), AnnoucementDetailsAddressOutput.class);
            annoucementDetailsLostFoundOutput.setAddress(addressOutput);
        }

        return annoucementDetailsLostFoundOutput;
    }


    public List<AnnouncementOutput> findMyAnnouncements(Long userId) {
        List<Announcement> announcements = announcementRepository.findByUserId(userId);

        // Mapeia cada elemento da lista de Announcement para AnnouncementOutput
        List<AnnouncementOutput> outputs = announcements.stream()
                .map(announcement -> modelMapper.map(announcement, AnnouncementOutput.class))
                .toList();

        return outputs;
    }

}
