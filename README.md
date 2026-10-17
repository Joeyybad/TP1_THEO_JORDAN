# GreenMove — Location et Facturation

Ce projet implémente deux hexagones : Location et Facturation dans une application Spring Boot.

## Utilisation

Compiler le projet : `mvn clean compile` </br>
Lancer tous les tests : `mvn test` </br>
Démarrer l'application : `mvn spring-boot:run` </br>

Le scénario de l'annexe C est couvert par `ScenarioBoutEnBoutTest`.</br>
Pour le jouer automatiquement : `mvn -Dtest=ScenarioBoutEnBoutTest test`.

Ce scénario vérifie une location payée ainsi qu'un paiement refusé, le blocage pour impayé, la régularisation puis une nouvelle location.