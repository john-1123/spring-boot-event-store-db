package com.example.springbooteventstoredb.accounts;

import com.example.springbooteventstoredb.accounts.AccountEvent.*;

import java.util.UUID;

sealed public interface Account {
    record Initial() implements Account {}
    record Opened(UUID id, String name ,Integer balance) implements Account {}
    record Closed(UUID id) implements Account {}

    static Account when(Account current, AccountEvent event) {
        return switch (event) {
            case AccountOpened accountOpened: {
                if(!(current instanceof Initial))
                    yield current;

                yield new Opened(accountOpened.id(), accountOpened.name(),accountOpened.balance());
            }
            case MoneySaved moneySaved: {
                if(!(current instanceof Opened))
                    yield current;

                yield new Opened(moneySaved.id(), moneySaved.name(), moneySaved.balance());
            }
            case AccountClosed accountClosed: {
                if(!(current instanceof Initial))
                    yield current;

                yield new Closed(accountClosed.id());
            }
            case null:
                throw new IllegalArgumentException("Event cannot be null!");
        };
    }

    static Account empty() {
        return new Initial();
    }

    static String mapToStreamId(UUID accountId) {
        return "Account-%s".formatted(accountId);
    }
}
