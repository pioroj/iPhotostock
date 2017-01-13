package pl.com.bottega.iphotostock.sales.infrastructure;


import pl.com.bottega.iphotostock.sales.model.LightBox;
import pl.com.bottega.iphotostock.sales.model.Client;
import pl.com.bottega.iphotostock.sales.model.LightBoxRepository;

import java.util.*;

public class InMemoryLightBoxRepository implements LightBoxRepository {

    private Map<Client, Collection<LightBox>> lightBoxMap = new HashMap<>();


    @Override
    public void put(LightBox lightBox) {
        Client client = lightBox.getOwner();
        lightBoxMap.putIfAbsent(client, new HashSet<>());
        Collection<LightBox> lightBoxCollection = lightBoxMap.get(client);

        lightBoxCollection.add(lightBox);
    }

    @Override
    public Collection<LightBox> getFor(Client client) {
        return lightBoxMap.get(client);
    }

    @Override
    public LightBox findLightBox(Client client, String lightBoxName) {
        Collection<LightBox> lightBoxCollection = lightBoxMap.get(client);
        LightBox customerLightBox = null;
        for (LightBox lightBox : lightBoxCollection) {
            if (lightBox.getName().equals(lightBoxName)) {
                customerLightBox = lightBox;
            }
        }
        if (customerLightBox == null)
            throw new IllegalArgumentException("There is not such lightbox");
        return customerLightBox;
    }

}
