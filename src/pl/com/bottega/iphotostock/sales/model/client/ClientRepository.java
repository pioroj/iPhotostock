package pl.com.bottega.iphotostock.sales.model.client;


public interface ClientRepository {

    Client get(String clientNumber);

    void update(Client client);
}
