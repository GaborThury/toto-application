package com.epam.training.toto.domain;

import java.util.Currency;

public class Price {
    private int value;
    private Currency currency;

    public Price(int value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

// NumberFormat.getCurrencyInstance(new Locale("hu", "HU")).format(max)



    //region Getters and Setters
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    //endregion

    @Override
    public String toString() {
        return "Price{" +
                "value=" + value +
                ", currency=" + currency +
                '}';
    }
}
