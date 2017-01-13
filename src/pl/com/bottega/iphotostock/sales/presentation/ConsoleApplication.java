package pl.com.bottega.iphotostock.sales.presentation;


import pl.com.bottega.iphotostock.sales.model.*;
import pl.com.bottega.iphotostock.sales.infrastructure.InMemoryProductRepository;
import pl.com.bottega.iphotostock.sales.model.ProductRepository;
import pl.com.bottega.iphotostock.sales.model.money.Money;

public class ConsoleApplication {

    public static void main(String[] args) {
        ProductRepository productRepository = new InMemoryProductRepository();

        Product product1 = productRepository.get("1");
        Product product2 = productRepository.get("2");
        Product product3 = productRepository.get("3");
        Product product4 = productRepository.get("4");

        Client client = new Client("Johny Bravo", new Address(), Money.valueOf(100));
        Client vipClient = new VIPClient("Johny VIP", new Address(), Money.ZERO, Money.valueOf(100));

        System.out.println(client.introduce());
        System.out.println(vipClient.introduce());

        Reservation reservation = new Reservation(vipClient);


        reservation.add(product1);
        reservation.add(product2);
        reservation.add(product3);
        reservation.add(product4);

        System.out.println("After adding items count: " + reservation.getItemsCount());


        Offer offer = reservation.generateOffer();

        boolean canAfford = vipClient.canAfford(offer.getTotalCost());
        System.out.println("Client can afford: " + canAfford);

        if (canAfford) {
            vipClient.charge(offer.getTotalCost(), "Test purchase");
            Purchase purchase = new Purchase(vipClient, product1, product2, product3);
            System.out.println("Client purchased: " + purchase.getItemsCount() + " pictures");
            System.out.println("Total cost: " + offer.getTotalCost());
        } else {
            System.out.println("Client can't afford that purchase.");
        }


    }

}
