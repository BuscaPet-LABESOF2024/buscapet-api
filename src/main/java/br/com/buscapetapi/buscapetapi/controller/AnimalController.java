package br.com.buscapetapi.buscapetapi.controller;

import br.com.buscapetapi.buscapetapi.dto.input.AnimalInput;
import br.com.buscapetapi.buscapetapi.dto.output.AnimalOutput;
import br.com.buscapetapi.buscapetapi.model.Animal;
import br.com.buscapetapi.buscapetapi.service.AnimalService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("animal")
public class AnimalController {

    private final AnimalService animalService;
    private final ModelMapper modelMapper;

    public AnimalController(AnimalService animalService, ModelMapper modelMapper) {
        this.animalService = animalService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/new-animal")
    public ResponseEntity<AnimalOutput> createAnimal(@Valid @RequestBody AnimalInput animalInput) {
        Animal createdAnimal = animalService.createAnimal(animalInput);
        AnimalOutput animalOutput = modelMapper.map(createdAnimal, AnimalOutput.class);
        return ResponseEntity.ok(animalOutput);
    }

    @PutMapping("/update-animal")
    public ResponseEntity<AnimalOutput> updateAnimal(@Valid @RequestBody AnimalInput animalInput) {
        Animal updatedAnimal = animalService.updateAnimal(animalInput);
        AnimalOutput animalOutput = modelMapper.map(updatedAnimal, AnimalOutput.class);
        return ResponseEntity.ok(animalOutput);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Animal> getAnimal(@PathVariable Long id) {
        Animal animal = animalService.findById(id);
        return ResponseEntity.ok(animal);
    }
}
