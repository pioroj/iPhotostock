package pl.com.bottega.iphotostock.sales.model.devices;


import pl.com.bottega.iphotostock.sales.model.LightBox;
import pl.com.bottega.iphotostock.sales.model.Client;

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

}
