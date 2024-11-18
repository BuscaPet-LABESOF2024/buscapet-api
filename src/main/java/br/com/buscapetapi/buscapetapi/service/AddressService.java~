package br.com.buscapetapi.buscapetapi.service;

import br.com.buscapetapi.buscapetapi.dto.input.AddressInput;
import br.com.buscapetapi.buscapetapi.model.Address;
import br.com.buscapetapi.buscapetapi.repository.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    public AddressService(AddressRepository addressRepository, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
    }

    public Address createAddress(AddressInput addressInput) {
        addressInput.setCreatedAt(LocalDateTime.now());
        addressInput.setUpdatedAt(LocalDateTime.now());
        Address address = modelMapper.map(addressInput, Address.class);
        return addressRepository.save(address);
    }

    public Address findById(Long id) {
        Optional<Address> existingAddress = addressRepository.findById(id);

        return existingAddress.orElse(null);
    }

    public Address updateAddress(AddressInput addressInput) {
        Optional<Address> existingAddress = addressRepository.findById(addressInput.getId());

        if (existingAddress.isPresent()) {
            Address address = existingAddress.get();

            address.setStreet(addressInput.getStreet());
            address.setNumber(addressInput.getNumber());
            address.setNeighborhood(addressInput.getNeighborhood());
            address.setCep(addressInput.getCep());
            address.setUpdatedAt(LocalDateTime.now());
            address.setReferencia(addressInput.getReferencia());
            address.setComplemento(addressInput.getComplemento());

            return addressRepository.save(address);
        }
        return null;
    }

    public List<String> findNeighborhoods() {
        return addressRepository.findDistinctNeighborhoods();
    }

}
