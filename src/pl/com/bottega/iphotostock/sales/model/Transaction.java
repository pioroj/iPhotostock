package pl.com.bottega.iphotostock.sales.model;


import pl.com.bottega.iphotostock.sales.money.Money;

import java.time.LocalDateTime;

public class Transaction {

    private Money value;
    private String description;
    private LocalDateTime timestamp;

    public Transaction(Money value, String description) {
        this.value = value;
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }
}
