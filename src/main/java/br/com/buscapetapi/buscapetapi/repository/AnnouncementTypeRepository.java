package br.com.buscapetapi.buscapetapi.repository;

import br.com.buscapetapi.buscapetapi.model.AnnouncementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementTypeRepository extends JpaRepository<AnnouncementType, Long> {
}
