package pl.com.bottega.iphotostock.sales.model;


import pl.com.bottega.iphotostock.sales.model.money.Money;

import java.util.*;

public class Offer {

    private List<Product> items;
    private Client client;

    public Offer(Collection<Product> items, Client client) {
        this.items = new LinkedList<>(items);
        this.client = client;
        sortProductsByPriceDesc();
    }

    public boolean sameAs(Offer other, Money money) {
        return false;
    }

    public int getItemsCount() {
        return items.size();
    }

    public Money getTotalCost() {
        Money totalCost = Money.ZERO;
        for (Product product : items) {
            Money productCost = product.calculatePrice(client);
            totalCost = totalCost.add(productCost);
        }
        return totalCost;
    }

    private void sortProductsByPriceDesc() {
        //lista ma metodę sort
        this.items.sort(new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                Money price1 = p1.calculatePrice(client);
                Money price2 = p2.calculatePrice(client);
                //sortowanie malejące
                return price2.compareTo(price1);
            }
        });
    }

    public List<Product> getItems() {
        return items;
    }
}
