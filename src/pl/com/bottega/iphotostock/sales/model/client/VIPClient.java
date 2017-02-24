package pl.com.bottega.iphotostock.sales.model.client;


import com.sun.org.apache.bcel.internal.generic.MONITORENTER;
import pl.com.bottega.iphotostock.sales.model.money.Money;

import java.util.Collection;

public class VIPClient extends Client {
    private Money creditLimit;

    public VIPClient(String name, Address address, Money balance, Money creditLimit) {
        super(name, address, ClientStatus.VIP, balance);
        this.creditLimit = creditLimit;
    }

    public VIPClient(String number, String name, Address address,
                     Money balance, Money creditLimit, boolean active, Collection<Transaction> transactions) {
        super(number, name, address, ClientStatus.VIP, balance, active, transactions);
        this.creditLimit = creditLimit;
    }

    @Override
    public boolean canAfford(Money money) {
        return balance.add(creditLimit).gte(money);
    }

    public Money getCreditLimit() {
        return creditLimit;
    }
}
