package com.example.springbooteventstoredb.api.backgroundworkers;

import com.eventstore.dbclient.EventStoreDBClient;
import com.example.springbooteventstoredb.core.events.EventBus;
import com.example.springbooteventstoredb.core.subscriptions.EventStoreDBSubscriptionToAll;
import com.example.springbooteventstoredb.core.subscriptions.SubscriptionCheckpointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;

public class EventStoreDBSubscriptionBackgroundWorker implements SmartLifecycle {
  private final SubscriptionCheckpointRepository subscriptionCheckpointRepository;
  private final EventStoreDBClient eventStore;
  private final EventBus eventBus;
  private EventStoreDBSubscriptionToAll subscription;
  private final Logger logger = LoggerFactory.getLogger(EventStoreDBSubscriptionBackgroundWorker.class);

  public EventStoreDBSubscriptionBackgroundWorker(
    EventStoreDBClient eventStore,
    SubscriptionCheckpointRepository subscriptionCheckpointRepository,
    EventBus eventBus
  ) {
    this.eventStore = eventStore;
    this.subscriptionCheckpointRepository = subscriptionCheckpointRepository;
    this.eventBus = eventBus;
  }

  @Override
  public void start() {
    try {
      subscription = new EventStoreDBSubscriptionToAll(
        eventStore,
        subscriptionCheckpointRepository,
        eventBus
      );
      subscription.subscribeToAll();
    } catch (Throwable e) {
      logger.error("Failed to start Subscription to All", e);
    }
  }

  @Override
  public void stop() {
    stop(() -> {});
  }

  @Override
  public boolean isRunning() {
    return subscription != null && subscription.isRunning();
  }

  @Override
  public int getPhase() {
    return Integer.MAX_VALUE;
  }

  @Override
  public boolean isAutoStartup() {
    return true;
  }

  @Override
  public void stop(Runnable callback) {
    if (!isRunning()) return;

    subscription.stop();

    callback.run();
  }
}
