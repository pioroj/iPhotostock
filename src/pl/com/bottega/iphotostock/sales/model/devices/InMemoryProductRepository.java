package pl.com.bottega.iphotostock.sales.model.devices;


import pl.com.bottega.iphotostock.sales.model.Clip;
import pl.com.bottega.iphotostock.sales.model.Picture;
import pl.com.bottega.iphotostock.sales.model.Product;
import pl.com.bottega.iphotostock.sales.money.Money;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InMemoryProductRepository implements ProductRepository {

    private static final Map<String, Product> REPOSITORY = new HashMap<>();

    //blok static służy do zaimplementowania pól statycznych
    static {
        Collection<String> tags = Arrays.asList("przyroda,", "motoryzacja");
        Product product1 = new Picture("1", "BMW", tags, Money.valueOf(3));
        Product product2 = new Picture("2", "Mercedes", tags, Money.valueOf(2));
        Product product3 = new Picture("3", "Porsche", tags, Money.valueOf(4));
        Product clip1 = new Clip("Wsciekle piesci", Money.valueOf(10), "4", 2l * 1000 * 60 * 2);
        Product clip2 = new Clip("Sum tzw. olimpijczyk", Money.valueOf(11), "4", 4l * 1000 * 60 * 2);
        REPOSITORY.put("1", product1);
        REPOSITORY.put("2", product2);
        REPOSITORY.put("3", product3);
        REPOSITORY.put("4", clip1);
        REPOSITORY.put("5", clip2);
    }

    @Override
    public void put(Product product) {
        REPOSITORY.putIfAbsent(product.getNumber(), product);
    }

    @Override
    public Product get(String number) {
        return REPOSITORY.get(number);
    }
}
