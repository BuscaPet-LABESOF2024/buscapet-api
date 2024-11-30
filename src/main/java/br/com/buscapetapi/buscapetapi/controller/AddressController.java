package br.com.buscapetapi.buscapetapi.controller;

import br.com.buscapetapi.buscapetapi.dto.input.AddressInput;
import br.com.buscapetapi.buscapetapi.dto.input.AddressUpdateRequestInput;
import br.com.buscapetapi.buscapetapi.dto.output.AddressDataProfileOutput;
import br.com.buscapetapi.buscapetapi.dto.output.AddressOutput;
import br.com.buscapetapi.buscapetapi.model.Address;
import br.com.buscapetapi.buscapetapi.service.AddressService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<AddressDataProfileOutput> updateAddress(HttpServletRequest request, @Valid @RequestBody AddressUpdateRequestInput addressUpdateRequestInput) {

        Long userId = (Long) request.getAttribute("userId");  // Extraído do token JWT

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Se não houver userId no request, retorna 401
        }

        AddressDataProfileOutput updatedAddress = addressService.updateAddress(userId, addressUpdateRequestInput);

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

    @GetMapping("/neighborhoods")
    public ResponseEntity<List<String>> getNeighborhoods() {
        List<String> neighborhoods = addressService.findAnnouncementsNeighborhoods();

        return ResponseEntity.ok(neighborhoods);
    }
}
