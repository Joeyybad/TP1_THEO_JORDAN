package fr.greenmove.facturation.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fr.greenmove.exterieur.PaiementSimule;
import fr.greenmove.facturation.application.ServiceFacturation;
import fr.greenmove.facturation.domaine.GrilleTarifaire;
import fr.greenmove.facturation.domaine.ports.Abonnements;
import fr.greenmove.facturation.domaine.ports.Factures;
import fr.greenmove.facturation.domaine.ports.Paiement;
import fr.greenmove.facturation.infrastructure.abonnements.AbonnementsParUsagers;
import fr.greenmove.facturation.infrastructure.paiement.PaiementParSimulation;
import fr.greenmove.facturation.infrastructure.persistance.FacturesEnMemoire;
import fr.greenmove.location.application.ConsulterUsagers;

@Configuration
public class ConfigurationFacturation {

    @Bean
    public Factures factures() {
        return new FacturesEnMemoire();
    }

    @Bean
    public GrilleTarifaire grilleTarifaire() {
        return new GrilleTarifaire();
    }

    @Bean
    public Paiement paiement(PaiementSimule paiementSimule) {
        return new PaiementParSimulation(paiementSimule);
    }

    @Bean
    public Abonnements abonnements(ConsulterUsagers usagers) {
        return new AbonnementsParUsagers(usagers);
    }

    @Bean
    public ServiceFacturation serviceFacturation(
            Factures factures,
            Paiement paiement,
            Abonnements abonnements,
            GrilleTarifaire grilleTarifaire) {
        return new ServiceFacturation(factures, paiement, abonnements, grilleTarifaire);
    }
}