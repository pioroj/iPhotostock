package pl.com.bottega.iphotostock.sales.model.purchase;


import pl.com.bottega.iphotostock.sales.model.client.Client;
import pl.com.bottega.iphotostock.sales.model.product.Product;

import java.util.*;

public class Reservation {

    private Client client;
    private Collection<Product> items;
    private String number;
    private boolean active = true;

    public Reservation(Client client) {
        this.client = client;
        this.items = new LinkedList<>();
        this.number = UUID.randomUUID().toString();
    }

    public void add(Product product) {
        if (items.contains(product))
            throw new IllegalArgumentException(String.format("Product %s is already in your this reservation.",product.getNumber()));
        product.ensureAvailable();
        items.add(product);
        product.reservedPer(client);
    }

    public void remove(Product product) {
        if (!items.contains(product))
            throw new IllegalArgumentException(String.format("Product %s is not added to this reservation.", product.getNumber()));
        items.remove(product);
        product.unreservedPer(client);
    }

    public Offer generateOffer() {
        Collection<Product> products = getActiveItems();
        if (products.isEmpty())
            throw new IllegalStateException("No active items in the reservation.");
        return new Offer(products, client);
    }

    private Collection<Product> getActiveItems() {
        Collection<Product> activeItems = new HashSet<>();
        for (Product product : items) {
            if (product.isActive())
                activeItems.add(product);
        }
        return activeItems;
    }

    public int getItemsCount() {
        return items.size();
    }

    public String getNumber() {
        return number;
    }

    public boolean isOwnedBy(String clientNumber) {
        return client.getNumber().equals(clientNumber);
    }

    public Client getOwner() {
        return client;
    }

    public void deactivate() {
        this.active = false;
    }

    public boolean isActive() {
        return active;
    }
}
