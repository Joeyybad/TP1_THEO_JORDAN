package fr.greenmove.location.infrastructure.persistance;

import java.util.Optional;

import org.springframework.stereotype.Component;

import fr.greenmove.location.domaine.IdLocation;
import fr.greenmove.location.domaine.IdUsager;
import fr.greenmove.location.domaine.Location;
import fr.greenmove.location.domaine.Statut;
import fr.greenmove.location.domaine.Ports.Locations;

@Component
public class LocationsJpa implements Locations {

    private final LocationJpaRepository repository;

    public LocationsJpa(LocationJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Location> parId(IdLocation id) {
        return repository.findById(id.valeur())
                .map(LocationMapper::versDomaine);
    }

    @Override
    public Optional<Location> enCoursPour(IdUsager usager) {
        return repository.findByUsagerIdAndStatut(usager.valeur(), Statut.EN_COURS.name())
                .map(LocationMapper::versDomaine);
    }

    @Override
    public void enregistrer(Location location) {
        LocationEntity entity = LocationMapper.versEntite(location);
        repository.save(entity);
    }
}