package com.example.springbooteventstoredb.core.config;

import com.example.springbooteventstoredb.core.events.EventBus;
import com.example.springbooteventstoredb.core.events.EventForwarder;
import com.example.springbooteventstoredb.core.serialization.EventSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CoreConfig {
  @Bean
  ObjectMapper defaultJSONMapper() {
    return EventSerializer.mapper;
  }

  @Bean
  EventBus eventBus(ApplicationEventPublisher applicationEventPublisher) {
    return new EventForwarder(applicationEventPublisher);
  }
}
