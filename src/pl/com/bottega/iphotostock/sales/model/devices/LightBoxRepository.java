package pl.com.bottega.iphotostock.sales.model.devices;


import pl.com.bottega.iphotostock.sales.model.LightBox;
import pl.com.bottega.iphotostock.sales.model.Client;

import java.util.Collection;

public interface LightBoxRepository {

    void put(LightBox lightBox);

    Collection<LightBox> getFor(Client client);

}
