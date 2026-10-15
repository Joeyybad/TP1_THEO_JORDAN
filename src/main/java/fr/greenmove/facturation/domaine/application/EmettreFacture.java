package fr.greenmove.facturation.domaine.application;

import java.time.Duration;

import fr.greenmove.facturation.domaine.IdFacture;
import fr.greenmove.facturation.domaine.IdLocation;
import fr.greenmove.facturation.domaine.IdUsager;
import fr.greenmove.location.domaine.TypeVehicule;

public interface EmettreFacture {
    IdFacture emettre(IdUsager usager, IdLocation location, TypeVehicule type, Duration duree);
}
