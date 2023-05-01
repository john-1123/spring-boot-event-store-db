package com.example.springbooteventstoredb.core.events;

import org.springframework.context.ApplicationEventPublisher;

public record EventForwarder(
  ApplicationEventPublisher applicationEventPublisher
) implements EventBus {

  @Override
  public <Event> void publish(EventEnvelope<Event> event) {
    applicationEventPublisher.publishEvent(event);
  }
}
