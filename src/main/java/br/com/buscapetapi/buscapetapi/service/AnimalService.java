package br.com.buscapetapi.buscapetapi.service;

import br.com.buscapetapi.buscapetapi.dto.input.AnimalInput;
import br.com.buscapetapi.buscapetapi.model.Animal;
import br.com.buscapetapi.buscapetapi.model.Announcement;
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
        Optional<Animal> existingAnimal = animalRepository.findById(animalInput.getId());

        if (existingAnimal.isPresent()) {
            Animal animal = existingAnimal.get();

            animal.setName(animalInput.getName());
            animal.setStatusAnimal(animalInput.getStatusAnimal());
            animal.setType(animalInput.getType());
            animal.setBreed(animalInput.getBreed());
            animal.setSize(animalInput.getSize());
            animal.setWeight(animalInput.getWeight());
            animal.setAge(animalInput.getAge());
            animal.setUpdatedAt(LocalDateTime.now());

            return animalRepository.save(animal);
        }
        return null;
    }

    public List<String> findBreeds() {
        return animalRepository.findDistinctBreeds();
    }
}
