package br.com.buscapetapi.buscapetapi.repository;

import br.com.buscapetapi.buscapetapi.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("SELECT DISTINCT a.neighborhood FROM Address a INNER JOIN Announcement an ON a.id = an.address.id")
    List<String> findDistinctNeighborhoods();
}
