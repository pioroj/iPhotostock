package pl.com.bottega.iphotostock.sales.model.client;


import pl.com.bottega.iphotostock.sales.model.money.Money;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Transaction {

    private Money value;
    private String description;
    private LocalDateTime timestamp;

    public Transaction(Money value, String description) {
        this.value = value;
        this.description = description;
        this.timestamp = LocalDateTime.now();
        timestamp.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    public Transaction(Money value, String description, LocalDateTime localDateTime) {
        this.value = value;
        this.description = description;
        this.timestamp = localDateTime;
    }

    public Money getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
