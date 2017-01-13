package pl.com.bottega.iphotostock.sales.presentation;


import pl.com.bottega.iphotostock.sales.application.AuthenticationProcess;
import pl.com.bottega.iphotostock.sales.application.LightBoxManagement;
import pl.com.bottega.iphotostock.sales.application.ProductCatalog;
import pl.com.bottega.iphotostock.sales.application.PurchaseProcess;
import pl.com.bottega.iphotostock.sales.infrastructure.InMemmoryReservationRepository;
import pl.com.bottega.iphotostock.sales.infrastructure.InMemoryClientRepository;
import pl.com.bottega.iphotostock.sales.infrastructure.InMemoryLightBoxRepository;
import pl.com.bottega.iphotostock.sales.infrastructure.InMemoryProductRepository;
import pl.com.bottega.iphotostock.sales.model.*;

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
        ClientRepository clientRepository = new InMemoryClientRepository();
        LightBoxRepository lightBoxRepository = new InMemoryLightBoxRepository();
        AuthenticationProcess authenticationProcess = new AuthenticationProcess(clientRepository);
        ReservationRepository reservationRepository = new InMemmoryReservationRepository();
        PurchaseProcess purchaseProcess = new PurchaseProcess(clientRepository, reservationRepository, productRepository);
        LightBoxManagement lightBoxManagement = new LightBoxManagement(lightBoxRepository, productRepository, clientRepository);
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
