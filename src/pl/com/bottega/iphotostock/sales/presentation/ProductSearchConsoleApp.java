package pl.com.bottega.iphotostock.sales.presentation;


import pl.com.bottega.iphotostock.sales.application.ProductCatalog;
import pl.com.bottega.iphotostock.sales.model.client.Address;
import pl.com.bottega.iphotostock.sales.model.client.Client;
import pl.com.bottega.iphotostock.sales.model.product.Product;
import pl.com.bottega.iphotostock.sales.model.client.VIPClient;
import pl.com.bottega.iphotostock.sales.infrastructure.memory.InMemoryProductRepository;
import pl.com.bottega.iphotostock.sales.model.money.Money;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ProductSearchConsoleApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductCatalog productCatalog = new ProductCatalog(new InMemoryProductRepository());
        Client client = new VIPClient("John Doe", new Address(), Money.ZERO, Money.valueOf(2000));
        while (true) {
            String name = getQuery(scanner);

            String[] tags = getTags(scanner);

            Money priceFrom = getMoney("Cena od",scanner);

            Money priceTo = getMoney("Cena do", scanner);

            List<Product> products = productCatalog.find(client,
                    name,
                    tags,
                    priceFrom,
                    priceTo
            );

            printProducts(client, products);
        }

    }

    private static void printProducts(Client client, List<Product> products) {
        System.out.println("Matching products: ");
        for (Product product : products) {
            System.out.println(
                    String.format("%s | %s %s",
                            product.getNumber(),
                            product.getName(),
                            product.calculatePrice(client))
                    );
        }
    }

    private static Money getMoney(String prompt, Scanner scanner) {
        while (true) {
            try {
                System.out.print(prompt + ": ");
                float f = scanner.nextFloat();
                scanner.nextLine();
                return Money.valueOf(f);
            } catch (InputMismatchException ex) {
                scanner.nextLine();
                System.out.println("Wprowadź poprwaną cenę np 9,99");
            }
        }
    }

    private static String[] getTags(Scanner scanner) {
        System.out.print("Tagi: ");
        String tagsRead = scanner.nextLine().trim();
        if (tagsRead.length() == 0)
            return null;
        else
            return scanner.nextLine().split(" ");
    }

    private static String getQuery(Scanner scanner) {
        System.out.print("Nazwa: ");
        return scanner.nextLine();
    }

}
