package fr.greenmove.facturation.infrastructure.evenements;

import java.time.Duration;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import fr.greenmove.facturation.application.EmettreFacture;
import fr.greenmove.facturation.domaine.IdLocation;
import fr.greenmove.facturation.domaine.IdUsager;
import fr.greenmove.facturation.domaine.TypeVehicule;
import fr.greenmove.location.domaine.evenements.LocationTerminee;

@Component
public class EcouteurLocationTerminee {

    private final EmettreFacture emettreFacture;

    public EcouteurLocationTerminee(EmettreFacture emettreFacture) {
        this.emettreFacture = emettreFacture;
    }

    @EventListener
    public void surLocationTerminee(LocationTerminee evt) {
        IdLocation locationId = new IdLocation(evt.idLocation());
        IdUsager usagerId = new IdUsager(evt.idUsager());
        TypeVehicule typeVehicule = TypeVehicule.valueOf(evt.typeVehicule());
        Duration duree = Duration.between(evt.debut(), evt.fin());

        emettreFacture.emettre(locationId, usagerId, typeVehicule, duree);
    }
}