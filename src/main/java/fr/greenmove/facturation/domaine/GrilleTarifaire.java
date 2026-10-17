package fr.greenmove.facturation.domaine;

import java.time.Duration;

// Code basic de grille tarifaire (méthode calculer) pour tester la compilation
public class GrilleTarifaire {

    public Montant calculer(TypeVehicule type, Duration duree, boolean estAbonne) {
        return new Montant(500);
    }
}
