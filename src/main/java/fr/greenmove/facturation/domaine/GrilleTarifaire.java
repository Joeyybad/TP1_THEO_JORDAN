package fr.greenmove.facturation.domaine;

public class GrilleTarifaire {

    public static final Montant priseEnCharge = new Montant(100);
    public static final Montant tarifVelo = new Montant(15);
    public static final Montant tarifTrottinette = new Montant(22);
    public static final Montant plafond = new Montant(1500);
    // tarif pour un abonné = -20% du tarif normal
    public static final float tarifAbo = 0.20f;
}
