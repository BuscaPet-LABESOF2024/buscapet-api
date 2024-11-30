package br.com.buscapetapi.buscapetapi.repository;

import br.com.buscapetapi.buscapetapi.dto.output.AnnouncementOutput;
import br.com.buscapetapi.buscapetapi.model.Announcement;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long>, JpaSpecificationExecutor<Announcement> {

    List<Announcement> findByUserId(Long userId);

}
