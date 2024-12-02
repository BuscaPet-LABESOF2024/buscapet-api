package br.com.buscapetapi.buscapetapi.service;

import br.com.buscapetapi.buscapetapi.dto.input.AddressInput;
import br.com.buscapetapi.buscapetapi.dto.input.AddressUpdateRequestInput;
import br.com.buscapetapi.buscapetapi.dto.output.AddressDataProfileOutput;
import br.com.buscapetapi.buscapetapi.model.Address;
import br.com.buscapetapi.buscapetapi.model.User;
import br.com.buscapetapi.buscapetapi.repository.AddressRepository;
import br.com.buscapetapi.buscapetapi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public AddressService(AddressRepository addressRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
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

    public AddressDataProfileOutput updateAddress(Long userId, AddressUpdateRequestInput addressInput) {
        // Busca o usuário pelo userId
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Pega o id do endereço do usuário
        Long addressId = user.getAddress();

        Address address;

        //Usuário não possui endereço associado
        if(addressId == null) {
            // Se o usuário não possui um endereço, cria um novo
            address = modelMapper.map(addressInput, Address.class);
            address.setCreatedAt(LocalDateTime.now());
            address.setUpdatedAt(LocalDateTime.now());

            // Salva o novo endereço
            addressRepository.save(address);

            // Associa o novo endereço ao usuário e salva o usuário
            user.setAddress(address.getId());
            userRepository.save(user);
        } else {
            // Se o endereço já existe, busca o endereço e atualiza seus campos
            address = addressRepository.findById(addressId)
                    .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));

            if (addressInput.getStreet() != null) address.setStreet(addressInput.getStreet());
            if (addressInput.getNeighborhood() != null) address.setNeighborhood(addressInput.getNeighborhood());
            if (addressInput.getNumber() != null) address.setNumber(addressInput.getNumber());
            if (addressInput.getCep() != null) address.setCep(addressInput.getCep());
            if (addressInput.getReferencia() != null) address.setReferencia(addressInput.getReferencia());
            if (addressInput.getComplemento() != null) address.setComplemento(addressInput.getComplemento());
            address.setUpdatedAt(LocalDateTime.now());

            // Salva as atualizações no endereço
            addressRepository.save(address);
        }
        // Retorna o endereço atualizado como um DTO
        return modelMapper.map(address, AddressDataProfileOutput.class);
    }

    public List<String> findAnnouncementsNeighborhoods() {
        return addressRepository.findDistinctNeighborhoods();
    }

}
