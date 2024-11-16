package br.com.buscapetapi.buscapetapi.repository;

import br.com.buscapetapi.buscapetapi.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    @Query("SELECT DISTINCT a.breed FROM Animal a")
    List<String> findDistinctBreeds();
}
