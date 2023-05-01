package com.example.springbooteventstoredb.accounts;

import com.eventstore.dbclient.EventStoreDBClient;
import com.example.springbooteventstoredb.core.entities.CommandHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.ApplicationScope;

@Configuration
class AccountConfig {
    @Bean
    AccountDecider accountDecider() {
        return new AccountDecider();
    }

    @Bean
    @ApplicationScope
    CommandHandler<Account, AccountCommand, AccountEvent> accountStore(
            EventStoreDBClient eventStore,
            AccountDecider decider
    ) {
        return new CommandHandler<>(
                eventStore,
                Account::when,
                decider::handle,
                Account::mapToStreamId,
                Account::empty
        );
    }
}
