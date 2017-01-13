package pl.com.bottega.iphotostock.sales.application;


import pl.com.bottega.iphotostock.sales.model.Client;
import pl.com.bottega.iphotostock.sales.model.Product;
import pl.com.bottega.iphotostock.sales.model.ProductRepository;
import pl.com.bottega.iphotostock.sales.model.money.Money;

import java.util.List;

public class ProductCatalog {

    private ProductRepository productRepository;

    public ProductCatalog(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> find(Client client, String nameQuery, String[] tags, Money priceFrom, Money priceTo) {
        //aspekty techniczne: autentykacja użytkownika, transakcja, połączenia z bazą danych
        return productRepository.find(client, nameQuery, tags, priceFrom, priceTo, true);
    }

}
