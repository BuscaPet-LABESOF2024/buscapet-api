package br.com.buscapetapi.buscapetapi.controller;

import br.com.buscapetapi.buscapetapi.model.Animal;
import br.com.buscapetapi.buscapetapi.service.AnimalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("animal")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @PostMapping("/new-animal")
    public ResponseEntity<Animal> createAnimal(@Valid @RequestBody Animal animalInput) {
        Animal createdAnimal = animalService.createAnimal(animalInput);
        return ResponseEntity.ok(createdAnimal);
    }

    @PutMapping("/update")
    public ResponseEntity<Animal> updateAnimal(@Valid @RequestBody Animal animalInput) {
        Animal updatedAnimal = animalService.updateAnimal(animalInput);
        return ResponseEntity.ok(updatedAnimal);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Animal> getAnimal(@PathVariable Long id) {
        Animal animal = animalService.findById(id);
        return ResponseEntity.ok(animal);
    }
}
