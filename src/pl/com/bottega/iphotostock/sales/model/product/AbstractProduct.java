package pl.com.bottega.iphotostock.sales.model.product;


import pl.com.bottega.iphotostock.sales.model.client.Client;
import pl.com.bottega.iphotostock.sales.model.money.Money;

public abstract class AbstractProduct implements Product {
    protected String number;
    private String name;
    protected Money catalogPrice;
    private boolean active;
    private Client reservationOwner;
    private Client buyer;

    public AbstractProduct(boolean active, String name, Money catalogPrice, String number) {
        this.active = active;
        this.name = name;
        this.catalogPrice = catalogPrice;
        this.number = number;
    }


    public abstract Money calculatePrice(Client client);

    @Override
    public boolean isAvailable() {
        return active && !isSold() && !isReserved();
    }

    private boolean isReserved() {
        return reservationOwner != null;
    }

    private boolean isSold() {
        return buyer != null;
    }

    @Override
    public void reservedPer(Client client) {
        ensureAvailable();
        reservationOwner = client;
    }

    @Override
    public void unreservedPer(Client client) {
        ensureReservedBy(client);
        reservationOwner = null;
    }

    @Override
    public void soldPer(Client client) {
        ensureReservedBy(client);
        buyer = client;
        unreservedPer(client);
    }

    private void ensureReservedBy(Client client) {
        if (!isReservedBy(client))
            throw new IllegalArgumentException(String.format("Picture %s is not reserved by %s", getNumber(), client.getName()));
    }

    private boolean isReservedBy(Client client) {
        return isReserved() && client.equals(reservationOwner);
    }

    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void deactivate() {
        active = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractProduct product = (AbstractProduct) o;

        return number != null ? number.equals(product.number) : product.number == null;

    }

    @Override
    public int hashCode() {
        return number != null ? number.hashCode() : 0;
    }
}
