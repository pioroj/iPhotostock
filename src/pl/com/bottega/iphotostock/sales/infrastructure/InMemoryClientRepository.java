package pl.com.bottega.iphotostock.sales.infrastructure;


import pl.com.bottega.iphotostock.sales.model.Address;
import pl.com.bottega.iphotostock.sales.model.Client;
import pl.com.bottega.iphotostock.sales.model.ClientRepository;
import pl.com.bottega.iphotostock.sales.model.VIPClient;
import pl.com.bottega.iphotostock.sales.model.money.Money;

import java.util.HashMap;
import java.util.Map;

public class InMemoryClientRepository implements ClientRepository{

    private static final Map<String, Client> REPOSITORY = new HashMap<>();

    static {
        Client client = new Client("Johny Bravo", new Address(), Money.valueOf(100));
        Client vipClient = new VIPClient("Johny VIP", new Address(), Money.ZERO, Money.valueOf(100));
        REPOSITORY.put(client.getNumber(), client);
        REPOSITORY.put(vipClient.getNumber(), vipClient);
    }

    @Override
    public Client get(String clientNumber) {
        return REPOSITORY.get(clientNumber);
    }
}
