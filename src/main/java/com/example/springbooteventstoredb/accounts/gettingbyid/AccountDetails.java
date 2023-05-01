package com.example.springbooteventstoredb.accounts.gettingbyid;

import com.example.springbooteventstoredb.core.events.EventMetadata;
import com.example.springbooteventstoredb.core.views.VersionedView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class AccountDetails implements VersionedView {
    @Id
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer balance;
    @JsonIgnore
    @Column(nullable = false)
    private long version;
    @JsonIgnore
    @Column(nullable = false)
    private long lastProcessedPosition;

    public AccountDetails() {

    }

    public AccountDetails(UUID id, String name,Integer balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public UUID getId() {
        return this.id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
    public Integer getBalance() {
        return this.balance;
    }
    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    @Override
    public long getLastProcessedPosition() {
        return this.lastProcessedPosition;
    }

    public void setLastProcessedPosition(long lastProcessedPosition) {
        this.lastProcessedPosition = lastProcessedPosition;
    }

    public long getVersion() {
        return this.version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public void setMetadata(EventMetadata eventMetadata) {
        this.version = eventMetadata.streamPosition();
        this.lastProcessedPosition = eventMetadata.logPosition();
    }

}
