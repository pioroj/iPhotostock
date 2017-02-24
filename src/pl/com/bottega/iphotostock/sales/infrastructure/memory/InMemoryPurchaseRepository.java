package pl.com.bottega.iphotostock.sales.infrastructure.memory;


import pl.com.bottega.iphotostock.sales.model.purchase.Purchase;
import pl.com.bottega.iphotostock.sales.model.purchase.PurchaseRepository;

import java.util.HashMap;
import java.util.Map;

public class InMemoryPurchaseRepository implements PurchaseRepository {

    private static final Map<String, Purchase> REPOSITORY = new HashMap<>();

    @Override
    public void put(Purchase purchase) {
        REPOSITORY.put(purchase.getNumber(), purchase);
    }
}
