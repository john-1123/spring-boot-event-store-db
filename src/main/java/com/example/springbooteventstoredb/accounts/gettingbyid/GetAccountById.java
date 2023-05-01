package com.example.springbooteventstoredb.accounts.gettingbyid;

import com.example.springbooteventstoredb.core.http.ETag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Null;
import org.springframework.retry.support.RetryTemplate;

import java.util.UUID;

public record GetAccountById(UUID id, @Null ETag eTag) {
    public static AccountDetails handle(
            AccountDetailsRepository repository,
            GetAccountById query
    ) {
        // example of long-polling
        RetryTemplate retryTemplate = RetryTemplate.builder()
                .retryOn(EntityNotFoundException.class)
                .exponentialBackoff(100, 2, 1000)
                .withinMillis(5000)
                .build();

        return retryTemplate.execute(context -> {
            var result = query.eTag() == null ?
                    repository.findById(query.id())
                    : repository.findByIdAndNeverVersion(query.id(), query.eTag().toLong());

            if (result.isEmpty()) {
                throw new EntityNotFoundException("Shopping cart not found");
            }

            return result.get();
        });
    }
}
