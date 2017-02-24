package pl.com.bottega.iphotostock.sales.model.product;


import pl.com.bottega.iphotostock.sales.model.client.Client;
import pl.com.bottega.iphotostock.sales.model.money.Money;

import java.util.List;


public interface ProductRepository {

    void put(Product product);

    Product get(String number);

    List<Product> find(Client client, String nameQuery, String[] tags, Money priceFrom, Money priceTo, boolean onlyActive);
}
