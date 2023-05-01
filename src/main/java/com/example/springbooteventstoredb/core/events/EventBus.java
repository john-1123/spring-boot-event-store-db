package com.example.springbooteventstoredb.core.events;

public interface EventBus {
  <Event> void publish(EventEnvelope<Event> event);
}
