package fr.greenmove.facturation.infrastructure.abonnements;

import fr.greenmove.exterieur.UsagersEnMemoire;
import fr.greenmove.facturation.domaine.IdUsager;
import fr.greenmove.facturation.domaine.ports.Abonnements;

public class AbonnementsParUsagers implements Abonnements {

    private final UsagersEnMemoire usagers;

    public AbonnementsParUsagers(UsagersEnMemoire usagers) {
        this.usagers = usagers;
    }

    @Override
    public boolean estAbonne(IdUsager usager) {
        return usagers.parId(usager.valeur())
                .map(UsagersEnMemoire.EntreeUsager::abonne)
                .orElse(false);
    }
}