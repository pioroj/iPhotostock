package pl.com.bottega.iphotostock.sales.model.devices;


import pl.com.bottega.iphotostock.sales.model.Product;


public interface ProductRepository {

    void put(Product product);

    Product get(String number);

}
