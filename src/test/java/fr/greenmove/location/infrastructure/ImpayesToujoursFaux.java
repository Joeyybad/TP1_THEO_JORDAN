package fr.greenmove.location.infrastructure;

import fr.greenmove.location.domaine.IdUsager;
import fr.greenmove.location.domaine.Ports.Impayes;

public class ImpayesToujoursFaux implements Impayes {
    @Override
    public boolean aDesImpayes(IdUsager usager) {
        return false;
    }
}