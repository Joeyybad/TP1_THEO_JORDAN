package fr.greenmove.facturation.domaine.erreurs;

public class FactureInconnue extends RuntimeException {
    public FactureInconnue(String id) {
        super("Facture inconnue : " + id);
    }

}
