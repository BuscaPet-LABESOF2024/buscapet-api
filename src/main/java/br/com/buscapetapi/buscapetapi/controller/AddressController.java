package br.com.buscapetapi.buscapetapi.controller;

import br.com.buscapetapi.buscapetapi.model.Address;
import br.com.buscapetapi.buscapetapi.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/new-address")
    public ResponseEntity<Address> createAddress(@Valid @RequestBody Address addressInput) {
        Address createdAddress = addressService.createAddress(addressInput);
        return ResponseEntity.ok(createdAddress);
    }

    @PutMapping("/update-address")
    public ResponseEntity<Address> updateAddress(@Valid @RequestBody Address addressInput) {
        Address updatedAddress = addressService.updateAddress(addressInput);
        return ResponseEntity.ok(updatedAddress);
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
