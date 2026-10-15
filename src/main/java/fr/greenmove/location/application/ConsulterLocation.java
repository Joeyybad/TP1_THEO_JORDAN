package fr.greenmove.location.application;

import java.util.Optional;

import fr.greenmove.location.domaine.IdLocation;
import fr.greenmove.location.domaine.Location;

public interface ConsulterLocation {

    /**
     * Récupère la location correspondant à l'identifiant fourni.
     *
     * @param id l'identifiant typé de la location recherchée
     * @return un Optional contenant la Location si trouvée, ou vide sinon
     */
    Optional<Location> consulter(IdLocation id);
}