package pl.com.bottega.iphotostock.sales.model.devices;


import pl.com.bottega.iphotostock.sales.model.Client;
import pl.com.bottega.iphotostock.sales.model.Product;
import pl.com.bottega.iphotostock.sales.money.Money;

import java.util.List;


public interface ProductRepository {

    void put(Product product);

    Product get(String number);

    List<Product> find(Client client, String nameQuery, String[] tags, Money priceFrom, Money priceTo, boolean onlyActive);
}
