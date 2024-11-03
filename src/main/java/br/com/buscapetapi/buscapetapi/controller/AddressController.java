package br.com.buscapetapi.buscapetapi.controller;

import br.com.buscapetapi.buscapetapi.dto.input.AddressInput;
import br.com.buscapetapi.buscapetapi.dto.output.AddressOutput;
import br.com.buscapetapi.buscapetapi.model.Address;
import br.com.buscapetapi.buscapetapi.service.AddressService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;
    private final ModelMapper modelMapper;

    public AddressController(AddressService addressService, ModelMapper modelMapper) {
        this.addressService = addressService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/new-address")
    public ResponseEntity<AddressOutput> createAddress(@Valid @RequestBody AddressInput addressInput) {
        Address createdAddress = addressService.createAddress(addressInput);
        AddressOutput addressOutput = modelMapper.map(createdAddress, AddressOutput.class);
        return ResponseEntity.ok(addressOutput);
    }

    @PutMapping("/update-address")
    public ResponseEntity<AddressOutput> updateAddress(@Valid @RequestBody AddressInput addressInput) {
        Address updatedAddress = addressService.updateAddress(addressInput);
        AddressOutput addressOutput = modelMapper.map(updatedAddress, AddressOutput.class);

        return ResponseEntity.ok(addressOutput);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddress(@PathVariable Long id) {
        Address address = addressService.findById(id);
        if (address != null) {
            return ResponseEntity.ok(address);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
