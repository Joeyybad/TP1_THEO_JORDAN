package fr.greenmove.location.infrastructure.persistance;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import fr.greenmove.location.application.ConsulterUsagers;
import fr.greenmove.location.domaine.IdUsager;
import fr.greenmove.location.domaine.Usager;
import fr.greenmove.location.domaine.Ports.Usagers;

public class UsagersEnMemoire implements Usagers, ConsulterUsagers {

    private final Map<IdUsager, Usager> usagers = new ConcurrentHashMap<>();

    public UsagersEnMemoire() {
        ajouter(new Usager(new IdUsager("usr-1"), false));
        ajouter(new Usager(new IdUsager("usr-2"), true));
    }

    public void ajouter(Usager usager) {
        this.usagers.put(usager.id(), usager);
    }

    public boolean estAbonne(String idUsager) {
        return this.usagers.values().stream()
                .filter(u -> u.id().valeur().equals(idUsager))
                .findFirst()
                .map(Usager::abonne)
                .orElse(false);
    }

    public Optional<Usager> trouver(String id) {
        return Optional.ofNullable(this.usagers.get(new IdUsager(id)));
    }

    public Optional<Usager> trouver(IdUsager id) {
        return Optional.ofNullable(this.usagers.get(id));
    }

    // --- Implémentation du port sortant de Location ---

    @Override
    public boolean existe(IdUsager usagerId) {
        return this.usagers.containsKey(usagerId);
    }
}