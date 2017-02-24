package pl.com.bottega.iphotostock.sales.presentation;


import pl.com.bottega.iphotostock.sales.application.AuthenticationProcess;
import pl.com.bottega.iphotostock.sales.application.LightBoxManagement;
import pl.com.bottega.iphotostock.sales.application.ProductCatalog;
import pl.com.bottega.iphotostock.sales.application.PurchaseProcess;
import pl.com.bottega.iphotostock.sales.infrastructure.csv.CSVClientRepository;
import pl.com.bottega.iphotostock.sales.infrastructure.memory.*;
import pl.com.bottega.iphotostock.sales.model.client.ClientRepository;
import pl.com.bottega.iphotostock.sales.model.lightbox.LightBoxRepository;
import pl.com.bottega.iphotostock.sales.model.product.ProductRepository;
import pl.com.bottega.iphotostock.sales.model.purchase.PurchaseRepository;
import pl.com.bottega.iphotostock.sales.model.purchase.ReservationRepository;

import java.util.Scanner;

public class LightBoxMain {

    private MainScreen mainScreen;
    private SearchScreen searchScreen;
    private ReservationScreen reservationScreen;
    private OfferScreen offerScreen;
    private LoginScreen loginScreen;
    private LightBoxScreen lightboxScreen;
    private LightBoxManagement lightBoxManagement;

    public LightBoxMain(){
        Scanner scanner = new Scanner(System.in);
        ProductRepository productRepository = new InMemoryProductRepository();
        ProductCatalog productCatalog = new ProductCatalog(new InMemoryProductRepository());
        ClientRepository clientRepository = new CSVClientRepository("E:\\Projekty\\PhotostockData\\clients");
        LightBoxRepository lightBoxRepository = new InMemoryLightBoxRepository();
        AuthenticationProcess authenticationProcess = new AuthenticationProcess(clientRepository);
        ReservationRepository reservationRepository = new InMemmoryReservationRepository();
        PurchaseRepository purchaseRepository = new InMemoryPurchaseRepository();
        PurchaseProcess purchaseProcess = new PurchaseProcess(clientRepository, reservationRepository,
                productRepository, purchaseRepository);
        LightBoxManagement lightBoxManagement = new LightBoxManagement(purchaseProcess, lightBoxRepository, productRepository, clientRepository);
        loginScreen = new LoginScreen(scanner, authenticationProcess);
        searchScreen = new SearchScreen(scanner, productCatalog, loginScreen);
        reservationScreen = new ReservationScreen(scanner, loginScreen, purchaseProcess);
        offerScreen = new OfferScreen(scanner, loginScreen, purchaseProcess);
        lightboxScreen = new LightBoxScreen(scanner, loginScreen, lightBoxManagement);
        mainScreen = new MainScreen(scanner, searchScreen, reservationScreen, offerScreen, lightboxScreen);
    }

    public void start() {
        loginScreen.print();
        mainScreen.print();
    }

    public static void main(String[] args) {
        new LightBoxMain().start();
    }

}
