package br.com.buscapetapi.buscapetapi.service;

import br.com.buscapetapi.buscapetapi.dto.input.AnimalInput;
import br.com.buscapetapi.buscapetapi.model.Animal;
import br.com.buscapetapi.buscapetapi.repository.AnimalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final ModelMapper modelMapper;

    public AnimalService(AnimalRepository animalRepository, ModelMapper modelMapper) {
        this.animalRepository = animalRepository;
        this.modelMapper = modelMapper;
    }

    public Animal createAnimal(AnimalInput animalInput) {
        animalInput.setCreatedAt(LocalDateTime.now());
        animalInput.setUpdatedAt(LocalDateTime.now());
        Animal animal = modelMapper.map(animalInput, Animal.class);
        return animalRepository.save(animal);
    }

    public Animal findById(Long id) {
        Optional<Animal> existingAnimal = animalRepository.findById(id);

        return existingAnimal.orElse(null);
    }

    public Animal updateAnimal(AnimalInput animalInput) {
        // Busca o animal existente pelo ID, ou lança uma exceção se não encontrado
        Animal existingAnimal = animalRepository.findById(animalInput.getId())
                .orElseThrow(() -> new RuntimeException("Animal not found with ID: " + animalInput.getId()));

        // Atualiza os campos permitidos se não forem nulos
        if (animalInput.getName() != null) existingAnimal.setName(animalInput.getName());
        if (animalInput.getStatusAnimal() != 0) existingAnimal.setStatusAnimal(animalInput.getStatusAnimal());
        if (animalInput.getType() != null) existingAnimal.setType(animalInput.getType());
        if (animalInput.getBreed() != null) existingAnimal.setBreed(animalInput.getBreed());
        if (animalInput.getSize() != null) existingAnimal.setSize(animalInput.getSize());
        if (animalInput.getWeight() != null) existingAnimal.setWeight(animalInput.getWeight());
        if (animalInput.getAge() != null) existingAnimal.setAge(animalInput.getAge());

        existingAnimal.setUpdatedAt(LocalDateTime.now()); // Atualiza a data de modificação

        // Salva e retorna o animal atualizado
        return animalRepository.save(existingAnimal);
    }


    public List<String> findAnnouncementsBreeds() {
        return animalRepository.findDistinctBreeds();
    }
}
