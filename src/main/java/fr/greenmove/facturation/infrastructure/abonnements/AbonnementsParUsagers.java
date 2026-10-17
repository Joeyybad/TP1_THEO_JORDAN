package fr.greenmove.facturation.infrastructure.abonnements;

import fr.greenmove.facturation.domaine.IdUsager;
import fr.greenmove.facturation.domaine.ports.Abonnements;
import fr.greenmove.location.application.ConsulterUsagers;

public class AbonnementsParUsagers implements Abonnements {

    private final ConsulterUsagers usagers;

    public AbonnementsParUsagers(ConsulterUsagers usagers) {
        this.usagers = usagers;
    }

    @Override
    public boolean estAbonne(IdUsager usager) {
        return usagers.estAbonne(usager.valeur());
    }
}