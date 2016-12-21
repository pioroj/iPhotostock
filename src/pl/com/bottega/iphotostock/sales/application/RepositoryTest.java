package pl.com.bottega.iphotostock.sales.application;


import pl.com.bottega.iphotostock.sales.model.Address;
import pl.com.bottega.iphotostock.sales.model.Client;
import pl.com.bottega.iphotostock.sales.model.LightBox;
import pl.com.bottega.iphotostock.sales.model.Product;
import pl.com.bottega.iphotostock.sales.model.devices.InMemoryLightBoxRepository;
import pl.com.bottega.iphotostock.sales.model.devices.InMemoryProductRepository;
import pl.com.bottega.iphotostock.sales.model.devices.ProductRepository;
import pl.com.bottega.iphotostock.sales.money.Money;

import java.util.Collection;

public class RepositoryTest {

    public static void main(String[] args) {

        Client johny = new Client("Johny Bravo", new Address(), Money.valueOf(100));
        Client danny = new Client("Danny", new Address(), Money.valueOf(100));

        InMemoryLightBoxRepository lightBoxRepository = new InMemoryLightBoxRepository();

        ProductRepository productRepository = new InMemoryProductRepository();

        Product product1 = productRepository.get("1");
        Product product2 = productRepository.get("2");
        Product product3 = productRepository.get("3");

        LightBox lightBox1 = new LightBox(johny, "Samochody");
        LightBox lightBox2 = new LightBox(johny, "Wyścigowe samochody");

        LightBox lightBox3 = new LightBox(danny, "Auta");
        LightBox lightBox4 = new LightBox(danny, "Szybkie auta");

        lightBox1.add(product1);
        lightBox1.add(product2);
        lightBox2.add(product3);

        lightBox3.add(product1);
        lightBox3.add(product2);
        lightBox4.add(product3);

        lightBoxRepository.put(lightBox1);
        lightBoxRepository.put(lightBox2);
        lightBoxRepository.put(lightBox3);
        lightBoxRepository.put(lightBox4);

        printLightBox(lightBox1);
        System.out.println("");
        printLightBoxes(lightBoxRepository.getFor(danny));
    }

    private static void printLightBoxes(Collection<LightBox> lightBoxes) {
        int nr = 1;
        for (LightBox lightbox : lightBoxes) {
            System.out.println(String.format("%d. %s - %s", nr,  lightbox.getName(), lightbox.getOwner().getName()));
            printLightBox(lightbox);
            nr++;
        }
    }

    private static void printLightBox(LightBox lightBox) {
        for (Product product : lightBox) {
            System.out.println(String.format("%s%s | %s",
                    (product.isActive() ? "" : "X "),
                    product.getNumber(),
                    product.calculatePrice(lightBox.getOwner())));
        }
    }

}
