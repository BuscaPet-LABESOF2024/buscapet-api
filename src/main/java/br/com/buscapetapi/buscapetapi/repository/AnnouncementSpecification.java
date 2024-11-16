package br.com.buscapetapi.buscapetapi.repository;

import br.com.buscapetapi.buscapetapi.model.Announcement;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class AnnouncementSpecification {

    public static Specification<Announcement> byAnnouncementType(Long announcementType) {
        return (root, query, builder) -> {
            if (announcementType != null) {
                return builder.equal(root.join("announcementType").get("id"), announcementType);
            } else {
                return builder.and();
            }
        };
    }

    public static Specification<Announcement> byAnimalType(String animalType) {
        return (root, query, builder) -> {
            if (animalType != null && !animalType.isEmpty()) {
                return builder.like(root.join("animal").get("type"), animalType);
            } else {
                return builder.and();
            }
        };
    }

    public static Specification<Announcement> byAnimalBreed(String animalBreed) {
        return (root, query, builder) -> {
            if (animalBreed != null && !animalBreed.isEmpty()) {
                return builder.like(root.join("animal").get("breed"), animalBreed);
            } else {
                return builder.and();
            }
        };
    }

    public static Specification<Announcement> byDate(LocalDate inicialDate, LocalDate finalDate) {
        return (root, query, builder) -> {
            if (inicialDate != null && finalDate != null) {
                // Filtrar entre as datas inicialDate e finalDate
                return builder.between(root.get("date"), inicialDate, finalDate);
            } else if (inicialDate == null && finalDate != null) {
                // Filtrar por data menor ou igual a finalDate
                return builder.lessThanOrEqualTo(root.get("date"), finalDate);
            } else if (inicialDate != null && finalDate == null) {
                // Filtrar por data maior ou igual a inicialDate
                return builder.greaterThanOrEqualTo(root.get("date"), inicialDate);
            } else {
                // Sem filtro de datas
                return builder.and();
            }
        };
    }

    public static Specification<Announcement> bySize(Integer size) {
        return (root, query, builder) -> {
            if (size != null) {
                return builder.equal(root.join("animal").get("size"), size);
            } else {
                return builder.conjunction();
            }
        };
    }

    public static Specification<Announcement> byNeighborhood(String neighborhood) {
        return (root, query, builder) -> {
            if (neighborhood != null && !neighborhood.isEmpty()) {
                return builder.like(root.join("address").get("neighborhood"), neighborhood);
            } else {
                return builder.and();
            }
        };
    }
}
