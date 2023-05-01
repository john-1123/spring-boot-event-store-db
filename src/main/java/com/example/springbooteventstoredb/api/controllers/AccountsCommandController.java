package com.example.springbooteventstoredb.api.controllers;

import com.example.springbooteventstoredb.accounts.Account;
import com.example.springbooteventstoredb.accounts.AccountCommand;
import com.example.springbooteventstoredb.accounts.AccountCommand.*;
import com.example.springbooteventstoredb.accounts.AccountEvent;
import com.example.springbooteventstoredb.accounts.gettingbyid.AccountDetails;
import com.example.springbooteventstoredb.api.requests.AccountsRequests;
import com.example.springbooteventstoredb.core.entities.CommandHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import static com.eventstore.dbclient.ExpectedRevision.noStream;

@Validated
@RestController
@RequestMapping("api/accounts")
class AccountsCommandController {
    private final CommandHandler<Account, AccountCommand, AccountEvent> store;

    AccountsCommandController(CommandHandler<Account, AccountCommand, AccountEvent> store) {
        this.store = store;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Void> open(@Valid @RequestBody AccountsRequests.Open request) throws URISyntaxException {
        var accountId = UUID.randomUUID();

        var result = store.handle(
            accountId,
            new OpenAccount(
                accountId,
                request.name(),
                request.balance()
            ),
            noStream()
        );

        return ResponseEntity
                .created(new URI("api/accounts/%s".formatted(accountId)))
                .eTag(result.value())
                .build();
    }
}
