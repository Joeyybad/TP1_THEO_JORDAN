package fr.greenmove.facturation.infrastructure.persistance;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import fr.greenmove.facturation.domaine.*;
import fr.greenmove.facturation.domaine.ports.Factures;

public class FacturesEnMemoire implements Factures {
    private final Map<IdFacture, Facture> stock = new ConcurrentHashMap<>();

    @Override
    public void enregistrer(Facture facture) {
        stock.put(facture.id(), facture);
    }

    @Override
    public Optional<Facture> parId(IdFacture id) {
        return Optional.ofNullable(stock.get(id));
    }

    @Override
    public Optional<Facture> parLocation(IdLocation idLocation) {
        return stock.values().stream()
                .filter(f -> f.idLocation().equals(idLocation))
                .findFirst();
    }

    @Override
    public List<Facture> impayeesDe(IdUsager usager) {
        return stock.values().stream()
                .filter(f -> f.idUsager().equals(usager))
                .filter(f -> f.statut() == Statut.IMPAYEE)
                .collect(Collectors.toList());
    }

}
