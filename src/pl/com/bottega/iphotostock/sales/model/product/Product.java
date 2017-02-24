package pl.com.bottega.iphotostock.sales.model.product;


import pl.com.bottega.iphotostock.sales.model.client.Client;
import pl.com.bottega.iphotostock.sales.model.money.Money;

public interface Product {

    Money calculatePrice(Client client);

    boolean isAvailable();

    void reservedPer(Client client);

    void unreservedPer(Client client);

    void soldPer(Client client);

    String getNumber();

    boolean isActive();

    String getName();

    void deactivate();

    default void ensureAvailable(){
        if(!isAvailable())
            throw new ProductNotAvailableException(this);
    }
}
