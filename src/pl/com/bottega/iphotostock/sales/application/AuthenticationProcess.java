package pl.com.bottega.iphotostock.sales.application;


import pl.com.bottega.iphotostock.sales.model.client.Client;
import pl.com.bottega.iphotostock.sales.model.client.ClientRepository;

public class AuthenticationProcess {

    private ClientRepository clientRepository;

    public AuthenticationProcess(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client authenticate(String clientNumber) {
        return clientRepository.get(clientNumber);
    }

}
