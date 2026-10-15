package fr.greenmove.location.infrastructure.persistance;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationJpaRepository extends JpaRepository<LocationEntity, String> {

    Optional<LocationEntity> findByUsagerIdAndStatut(String usagerId, String statut);
}