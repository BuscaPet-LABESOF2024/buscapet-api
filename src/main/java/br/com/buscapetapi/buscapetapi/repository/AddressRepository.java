package br.com.buscapetapi.buscapetapi.repository;

import br.com.buscapetapi.buscapetapi.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
