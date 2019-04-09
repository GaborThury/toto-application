package com.epam.training.toto.domain;

import java.util.Currency;

public class Prize {
    private int amount;
    private Currency currency;

    public Prize(int amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

}
