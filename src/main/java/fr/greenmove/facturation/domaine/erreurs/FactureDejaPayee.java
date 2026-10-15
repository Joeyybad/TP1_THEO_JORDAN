package fr.greenmove.facturation.domaine.erreurs;

public class FactureDejaPayee extends RuntimeException {
    public FactureDejaPayee() {
        super("Facture déjà payée");
    }

}
