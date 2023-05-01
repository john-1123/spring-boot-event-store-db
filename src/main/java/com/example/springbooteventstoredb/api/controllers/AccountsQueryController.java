package com.example.springbooteventstoredb.api.controllers;

import com.example.springbooteventstoredb.accounts.gettingbyid.AccountDetails;
import com.example.springbooteventstoredb.accounts.gettingbyid.AccountDetailsRepository;
import com.example.springbooteventstoredb.accounts.gettingbyid.GetAccountById;
import com.example.springbooteventstoredb.core.http.ETag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import static com.example.springbooteventstoredb.accounts.gettingbyid.GetAccountById.handle;

import java.util.UUID;

@RestController
@RequestMapping("api/accounts")
class AccountsQueryController {
    private final AccountDetailsRepository repository;

    AccountsQueryController(AccountDetailsRepository repository) {
        this.repository = repository;
    }

    @GetMapping("{id}")
    ResponseEntity<AccountDetails> getById(
        @PathVariable UUID id,
        @RequestHeader(name = HttpHeaders.IF_NONE_MATCH) @Nullable ETag ifNoneMatch
    ) {
        var result = handle(repository, new GetAccountById(id, ifNoneMatch));

        return ResponseEntity
                .ok()
                .eTag(ETag.weak(result.getVersion()).value())
                .body(result);
    }

}
