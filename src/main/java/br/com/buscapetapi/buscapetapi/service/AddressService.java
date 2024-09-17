package br.com.buscapetapi.buscapetapi.service;

import br.com.buscapetapi.buscapetapi.model.Address;
import br.com.buscapetapi.buscapetapi.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address createAddress(Address addressInput) {
        addressInput.setCreatedAt(LocalDateTime.now());
        addressInput.setUpdatedAt(LocalDateTime.now());
        return addressRepository.save(addressInput);
    }

    public Address findById(Long id) {
        Optional<Address> existingAddress = addressRepository.findById(id);

        if (existingAddress.isPresent()) {
            return existingAddress.get();
        }
        return null;
    }

    public Address updateAddress(Address addressInput) {
        Optional<Address> existingAddress = addressRepository.findById(addressInput.getId());

        if (existingAddress.isPresent()) {
            Address address = existingAddress.get();

            address.setStreet(addressInput.getStreet());
            address.setNumber(addressInput.getNumber());
            address.setNeighborhood(addressInput.getNeighborhood());
            address.setCep(addressInput.getCep());
            address.setCreatedAt(addressInput.getCreatedAt()); // Preservar a data de criação
            address.setUpdatedAt(LocalDateTime.now());

            return addressRepository.save(address);
        }
        return null;
    }
}
