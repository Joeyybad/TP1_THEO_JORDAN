package fr.greenmove.location.infrastructure.impayes;

import fr.greenmove.facturation.application.ConsulterImpayes;
import fr.greenmove.location.domaine.IdUsager;
import fr.greenmove.location.domaine.Ports.Impayes;

public class ImpayesParFacturation implements Impayes {

    private final ConsulterImpayes consulterImpayes;

    public ImpayesParFacturation(ConsulterImpayes consulterImpayes) {
        this.consulterImpayes = consulterImpayes;
    }

    @Override
    public boolean aDesImpayes(IdUsager usagerId) {
        return consulterImpayes.aDesImpayes(usagerId.valeur());
    }
}