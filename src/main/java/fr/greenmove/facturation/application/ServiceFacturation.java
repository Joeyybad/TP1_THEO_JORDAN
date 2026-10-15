package fr.greenmove.facturation.application;

import java.time.Duration;

import fr.greenmove.facturation.domaine.*;
import fr.greenmove.facturation.domaine.Ports.Abonnements;

public class ServiceFacturation implements EmettreFacture, ConsulterFacture, RegulariserFacture {

    private final EmettreFacture emetteur;
    private final ConsulterFacture consulteur;
    private final RegulariserFacture regulariseur;
    private final Abonnements abonnements;
    private final GrilleTarifaire grilleTarifaire;

    public ServiceFacturation(EmettreFacture emetteur, ConsulterFacture consulteur, RegulariserFacture regulariseur,
            Abonnements abonnements, GrilleTarifaire grilleTarifaire) {
        this.emetteur = emetteur;
        this.consulteur = consulteur;
        this.regulariseur = regulariseur;
        this.abonnements = abonnements;
        this.grilleTarifaire = grilleTarifaire;
    }

    @Override
    public Facture emettre(IdLocation locationId, IdUsager usager, TypeVehicule type, Duration duree) {
        return emetteur.emettre(locationId, usager, type, duree);
    }

    @Override
    public Facture regulariser(IdFacture idFacture) {
        return regulariseur.regulariser(idFacture);
    }

    @Override
    public java.util.Optional<Facture> consulter(IdFacture id) {
        return consulteur.consulter(id);
    }

}
