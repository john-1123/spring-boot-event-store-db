package com.example.springbooteventstoredb.accounts.gettingbyid;

import com.example.springbooteventstoredb.accounts.AccountEvent.*;
import com.example.springbooteventstoredb.core.events.EventEnvelope;
import com.example.springbooteventstoredb.core.projections.JPAProjection;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
class AccountDetailsProjection extends JPAProjection<AccountDetails, UUID> {
    protected AccountDetailsProjection(AccountDetailsRepository repository) {
        super(repository);
    }

    @EventListener
    void handleAccountOpened(EventEnvelope<AccountOpened> eventEnvelope) {
        add(eventEnvelope, () -> {
            var event = eventEnvelope.data();
            return new AccountDetails(
                event.id(),
                event.name(),
                event.balance()
            );
        });
    }

    @EventListener
    void handleMoneySaved(EventEnvelope<MoneySaved> eventEnvelope) {
        getAndUpdate(eventEnvelope.data().id(), eventEnvelope, view -> {
           var event = eventEnvelope.data();
           var existingBalance = view.getBalance();
           var changeBalance = event.balance();
           view.setBalance(existingBalance + changeBalance);
           return view;
        });
    }

    @EventListener
    void handleAccountClosed(EventEnvelope<AccountClosed> eventEnvelope) {
        var event = eventEnvelope.data();
        deleteById(event.id());
    }
}
