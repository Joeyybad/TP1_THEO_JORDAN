package fr.greenmove.facturation.domaine.erreurs;

public class LocationSansFacture extends RuntimeException {

    public LocationSansFacture(String id) {
        super("Location sans facture : " + id);
    }

}
