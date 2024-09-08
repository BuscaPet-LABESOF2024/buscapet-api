package br.com.buscapetapi.buscapetapi.service;

import br.com.buscapetapi.buscapetapi.model.Animal;
import br.com.buscapetapi.buscapetapi.repository.AnimalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public Animal createAnimal(Animal animalInput) {
        animalInput.setCreatedAt(LocalDateTime.now());
        animalInput.setUpdatedAt(LocalDateTime.now());
        return animalRepository.save(animalInput);
    }

    public Animal findById(Long id) {
        Optional<Animal> existingAnimal = animalRepository.findById(id);

        if (existingAnimal.isPresent()) {
            Animal animal = existingAnimal.get();
            return animal;
        }
        return null;
    }

    public Animal updateAnimal(Animal animalInput) {
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
            animal.setCreatedAt(animalInput.getCreatedAt());
            animal.setUpdatedAt(LocalDateTime.now());

            return animalRepository.save(animal);
        }
        return null;
    }
}
