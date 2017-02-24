package pl.com.bottega.iphotostock.sales.model.client;


import pl.com.bottega.iphotostock.sales.model.money.Money;

import java.util.Collection;
import java.util.LinkedList;

public class Client {

    private String name;
    private Address address;
    private ClientStatus status;
    protected Money balance;

    private Collection<Transaction> transactions;

    private boolean active;
    private String number;
    private static int clientNumber;
    public Client(String name, Address address, ClientStatus status, Money initialBalance) {
        this(nextNumber(), name, address, status, initialBalance, true, new LinkedList<>());
        if (!initialBalance.equals(Money.ZERO))
            this.transactions.add(new Transaction(initialBalance, "Opening account"));
    }


    public Client(String number, String name, Address address,
                  ClientStatus status, Money initialBalance, boolean active, Collection<Transaction> transactions) {
        this.name = name;
        this.address = address;
        this.status = status;
        this.balance = initialBalance;
        this.transactions = new LinkedList<>(transactions);
        this.number = number;
        this.active = active;
    }

    private static String nextNumber() {
        clientNumber += 100;
        return String.valueOf(clientNumber);
    }

    public Client(String name, Address address, Money balance) {
        this(name, address, ClientStatus.STANDARD, balance);
    }

    public boolean canAfford(Money money) {
        return balance.gte(money);
    }

    public void charge(Money money, String reason) {
        if (money.lte(Money.ZERO))
            throw new IllegalArgumentException("Negative charge amount prohibited");
        if (canAfford(money)) {
            Transaction chargeTransaction = new Transaction(money.opposite(), reason);
            transactions.add(chargeTransaction);
            balance = balance.subtract(money);
        } else {
            String template = "Client balance is %s and requested amount was %s";
            String message = String.format(template, balance, money);
            throw new CantAffordException(message);
        }
    }

    public void recharge(Money money) {
        if (money.lte(Money.ZERO))
            throw new IllegalArgumentException("Negative recharge amount prohibited");
        Transaction transaction = new Transaction(money, "Recharge account");
        transactions.add(transaction);
        balance = balance.add(money);
    }

    public String introduce() {
        String statusName = status.getStatusName();
        return String.format("%s - %s", name, statusName);
    }

    public String getName() {
        return name;
    }

    public void deactivate() {
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public String getNumber() {
        return number;
    }

    public ClientStatus getStatus() {
        return status;
    }

    public Money getBalance() {
        return balance;
    }

    public Collection<Transaction> getTransactions() {
        return new LinkedList<>(transactions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;

        Client client = (Client) o;

        if (name != null ? !name.equals(client.name) : client.name != null) return false;
        return number != null ? number.equals(client.number) : client.number == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        return result;
    }
}
