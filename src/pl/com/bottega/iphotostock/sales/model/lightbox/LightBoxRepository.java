package pl.com.bottega.iphotostock.sales.model.lightbox;


import pl.com.bottega.iphotostock.sales.model.client.Client;

import java.util.Collection;

public interface LightBoxRepository {

    void put(LightBox lightBox);

    Collection<LightBox> getFor(Client client);

    LightBox findLightBox(Client client, String lightBoxName);

    Collection<String> getLightBoxNames(Client client);
}
