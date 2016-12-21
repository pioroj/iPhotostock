package pl.com.bottega.iphotostock.sales.model;


import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;


public class LightBox implements Iterable<Product> {

    private Client client;
    private String name;
    private Collection<Product> items = new LinkedList<>();

    public LightBox(Client client, String name) {
        this.client = client;
        this.name = name;
    }

    public void add(Product product) {
        if (items.contains(product))
            throw new IllegalArgumentException(String.format("Picture %s is already in your LightBox", product.getNumber()));
       product.ensureAvailable();
        items.add(product);
    }

    public void remove(Product product) {
        if (!items.contains(product))
            throw new IllegalArgumentException(String.format("Picture %s is not in your LightBox", product.getNumber()));
        items.remove(product);
    }

    public void rename(String newName) {
        this.name = newName;
    }

    @Override
    public Iterator<Product> iterator() {
        return items.iterator();
    }

    public String getName() {
        return name;
    }

    public Client getOwner() {
        return client;
    }

    public static LightBox joined(Client client, String name,  LightBox ... lightBoxes) {
        LightBox newLightBox = new LightBox(client, name);

        for (LightBox lb : lightBoxes) {
            for (Product product : lb) {
                if (!newLightBox.items.contains(product) && product.isAvailable())
                    newLightBox.items.add(product);
            }
        }

        return newLightBox;
    }
}
