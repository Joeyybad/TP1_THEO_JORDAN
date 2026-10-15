package fr.greenmove.facturation.application;

import java.time.Duration;

import fr.greenmove.facturation.domaine.Facture;
import fr.greenmove.facturation.domaine.IdLocation;
import fr.greenmove.facturation.domaine.IdUsager;
import fr.greenmove.facturation.domaine.TypeVehicule;

public interface EmettreFacture {

    Facture emettre(IdLocation location, IdUsager usager, TypeVehicule type, Duration duree);
}