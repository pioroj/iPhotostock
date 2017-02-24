package pl.com.bottega.iphotostock.sales.model.purchase;


import pl.com.bottega.iphotostock.sales.model.client.Client;
import pl.com.bottega.iphotostock.sales.model.product.Product;

import java.util.*;

public class Purchase {

    private Client client;
    private Date purchaseDate = new Date();
    private List<Product> items;
    private String number;

    public Purchase(Client client, Collection<Product> items) {
        this.client = client;
        this.items = new LinkedList<>(items);
        this.number = UUID.randomUUID().toString();
        sortProductsByNumber();
        markProductsAsSold();
    }

    private void markProductsAsSold() {
        for (Product product : items) {
            product.soldPer(client);
        }
    }

    private void sortProductsByNumber() {
        this.items.sort(new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                String n1 = p1.getNumber();
                String n2 = p2.getNumber();
                //sortowanie rosnące, zeby było malejące wystarczy dać minus przed n1
                return n1.compareTo(n2);
            }
        });
    }

    public Purchase(Client client, Product ... items) { //dynamiczna liczba parametrów, pictures jest arrayem
        this(client, Arrays.asList(items));
    }

    public int getItemsCount() {
        return 0;
    }

    public String getNumber() {
        return number;
    }
}
