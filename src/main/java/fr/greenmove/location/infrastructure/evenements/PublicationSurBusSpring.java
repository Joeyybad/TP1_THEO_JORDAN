package fr.greenmove.location.infrastructure.evenements;

import org.springframework.context.ApplicationEventPublisher;

import fr.greenmove.location.domaine.Ports.PublicationEvenements;

public class PublicationSurBusSpring implements PublicationEvenements {

    private final ApplicationEventPublisher publisher;

    public PublicationSurBusSpring(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publier(Object evenement) {
        publisher.publishEvent(evenement);
    }
}