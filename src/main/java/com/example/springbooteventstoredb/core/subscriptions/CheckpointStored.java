package com.example.springbooteventstoredb.core.subscriptions;

import java.time.OffsetDateTime;

record CheckpointStored(
  String subscriptionId,
  long position,
  OffsetDateTime checkpointAt
) {
}
