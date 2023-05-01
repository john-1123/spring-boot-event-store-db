package com.example.springbooteventstoredb.accounts;

import java.util.UUID;

public sealed interface AccountCommand {
    record OpenAccount(UUID id, String name, Integer balance) implements AccountCommand {}
    record SaveMoney(UUID id, String name, Integer balance) implements AccountCommand {}
    record CloseAccount(UUID id) implements AccountCommand {}
}
