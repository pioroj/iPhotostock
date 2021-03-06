package pl.com.bottega.iphotostock.sales.presentation;


import pl.com.bottega.iphotostock.sales.model.client.Address;
import pl.com.bottega.iphotostock.sales.model.client.Client;
import pl.com.bottega.iphotostock.sales.model.lightbox.LightBox;
import pl.com.bottega.iphotostock.sales.model.product.Product;
import pl.com.bottega.iphotostock.sales.infrastructure.memory.InMemoryProductRepository;
import pl.com.bottega.iphotostock.sales.model.product.ProductRepository;
import pl.com.bottega.iphotostock.sales.model.money.Money;

public class LightBoxTest {

    public static void main(String[] args) {

        ProductRepository productRepository = new InMemoryProductRepository();

        Product product1 = productRepository.get("1");
        Product product2 = productRepository.get("2");
        Product product3 = productRepository.get("3");

        Client client = new Client("Johny Bravo", new Address(), Money.valueOf(100));
        Client danny = new Client("Danny", new Address(), Money.valueOf(100));

        LightBox lightBox1 = new LightBox(client, "Samochody");
        LightBox lightBox1a = new LightBox(client, "Samochody");
        LightBox lightBox2 = new LightBox(client, "Wyścigowe samochody");
        LightBox lightBox3 = new LightBox(danny, "BMW");

        lightBox1.add(product1);
        lightBox1a.add(product2);
        printLightBoxes(lightBox1, lightBox1a);

        /*lightBox1.add(product1);
        lightBox1.add(product2);
        lightBox1.add(product3);

        lightBox2.add(product1);

        lightBox3.add(product3);

        product1.deactivate();

        printLightBoxes(lightBox1, lightBox2, lightBox3);

        LightBox l = LightBox.joined(client, "Joined lightbox", lightBox1, lightBox2, lightBox3);
        System.out.println("Joined:");
        printLightBox(l);*/
    }

    private static void printLightBoxes(LightBox ... lightBoxes) {
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
