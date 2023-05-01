package com.example.springbooteventstoredb.core.views;

import com.example.springbooteventstoredb.core.events.EventMetadata;

public interface VersionedView {
    long getLastProcessedPosition();
    void setMetadata(EventMetadata eventMetadata);
}
