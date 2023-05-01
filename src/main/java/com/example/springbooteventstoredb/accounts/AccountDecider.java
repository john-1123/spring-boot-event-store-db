package com.example.springbooteventstoredb.accounts;

import com.example.springbooteventstoredb.accounts.Account.*;
import com.example.springbooteventstoredb.accounts.AccountCommand.*;
import com.example.springbooteventstoredb.accounts.AccountEvent.*;
public class AccountDecider {
    public AccountDecider() {}

    public AccountEvent handle(AccountCommand command, Account account) {
        return switch (command) {
            case OpenAccount openAccount:
                yield handle(account, openAccount);
            case SaveMoney saveMoney:
                yield handle(account, saveMoney);
            case  CloseAccount closeAccount:
                yield handle(account, closeAccount);
        };
    }

    private AccountOpened handle(Account account, OpenAccount command) {
        if(!(account instanceof Initial))
            throw new IllegalStateException("Opening Account in '%s' status is not allowed.".formatted(account.getClass().getName()));
        return new AccountOpened(command.id(), command.name(), command.balance());
    }

    private MoneySaved handle(Account account, SaveMoney command) {
        if (!(account instanceof Opened))
            throw new IllegalStateException("Saving Money to Account in '%s' status is not allowed.".formatted(account.getClass().getName()));
        return new MoneySaved(command.id(), command.name(), command.balance());
    }

    private AccountClosed handle(Account account, CloseAccount command) {
        if (!(account instanceof Opened))
            throw new IllegalStateException("Closing Account in '%s' status is not allowed.".formatted(account.getClass().getName()));
        return new AccountClosed(command.id());
    }
}
