package pl.com.bottega.iphotostock.sales.application;


import pl.com.bottega.iphotostock.sales.model.*;

import java.util.ArrayList;
import java.util.Collection;

public class LightBoxManagement {

    private LightBoxRepository lightBoxRepository;
    private ProductRepository productRepository;
    private ClientRepository clientRepository;

    public LightBoxManagement(LightBoxRepository lightBoxRepository,
                              ProductRepository productRepository,
                              ClientRepository clientRepository) {
        this.lightBoxRepository = lightBoxRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
    }

    public Collection<String> getLightBoxNames(String customerNumber) {
        Client client = clientRepository.get(customerNumber);
        Collection<LightBox> lightBoxCollection = lightBoxRepository.getFor(client);

        Collection<String> lightBoxNames = new ArrayList<>();
        for (LightBox lightBox : lightBoxCollection) {
            lightBoxNames.add(lightBox.getName());
        }
        return lightBoxNames;
    }

    public LightBox getLightBox(String customerNumber, String lightBoxName) {
        if (lightBoxName == null)
            throw new IllegalArgumentException("Wrong lightbox name.");
        Client client = clientRepository.get(customerNumber);
        return lightBoxRepository.findLightBox(client, lightBoxName);
    }

    public void addProduct(String customerNumber, String lightBoxName, String productNumber) {
        if (customerNumber == null)
            throw new IllegalArgumentException(String.format("There is no such client - %s.", customerNumber));

        Product product = productRepository.get(productNumber);
        if (productNumber == null || product == null) {
            throw new IllegalArgumentException(String.format("There is no such product - %s.", productNumber));
        }

        LightBox lb = getLightBox(customerNumber, lightBoxName);
        if (lb == null) {
            lb = new LightBox(clientRepository.get(customerNumber), lightBoxName);
            lightBoxRepository.put(lb);
        }
        lb.add(product);
    }
}
