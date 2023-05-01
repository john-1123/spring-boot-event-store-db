package com.example.springbooteventstoredb.accounts;

import java.util.UUID;

public sealed interface AccountEvent {
    record AccountOpened(UUID id, String name, Integer balance) implements AccountEvent {}
    record MoneySaved(UUID id, String name, Integer balance) implements AccountEvent {}
    record AccountClosed(UUID id) implements AccountEvent {}
}
