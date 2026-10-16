package fr.greenmove.facturation.infrastructure.abonnements;

import fr.greenmove.facturation.domaine.IdUsager;
import fr.greenmove.facturation.domaine.ports.Abonnements;
import fr.greenmove.facturation.infrastructure.persistance.UsagersEnMemoire;

public class AbonnementsParUsagers implements Abonnements {
    private final UsagersEnMemoire usagers;

    public AbonnementsParUsagers(UsagersEnMemoire usagers) {
        this.usagers = usagers;
    }

    @Override
    public boolean estAbonne(IdUsager usager) {
        return usagers.trouver(usager.valeur())
                .map(u -> u.abonne())
                .orElse(false);
    }
}