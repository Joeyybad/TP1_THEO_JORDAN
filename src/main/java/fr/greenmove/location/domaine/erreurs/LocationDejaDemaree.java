package fr.greenmove.location.domaine.erreurs;
import fr.greenmove.location.domaine.IdLocation;

public class LocationDejaDemaree extends RuntimeException {

    LocationDejaDemaree(IdLocation id) {
        super("Location déjà démarrée : " + id);
    }
}
