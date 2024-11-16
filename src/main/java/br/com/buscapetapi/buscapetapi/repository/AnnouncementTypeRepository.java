package br.com.buscapetapi.buscapetapi.repository;

import br.com.buscapetapi.buscapetapi.model.AnnouncementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementTypeRepository extends JpaRepository<AnnouncementType, Long> {
    @Query("SELECT DISTINCT a.description FROM AnnouncementType a")
    List<String> findTypes();
}
