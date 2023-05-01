package com.example.springbooteventstoredb.core.events;

public record EventMetadata (
    String eventId,
    String eventType,
    long streamPosition,
    long logPosition
){}
