package com.example.springbooteventstoredb.api.requests;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

public final class AccountsRequests {
    public record Open(@NotNull String name, @NotNull Integer balance) {}

    @Validated
    public record SaveMoney(@NotNull UUID Id, @NotNull String name, @NotNull Integer balance) {}

    public record Close(@NotNull UUID id) {}
}
