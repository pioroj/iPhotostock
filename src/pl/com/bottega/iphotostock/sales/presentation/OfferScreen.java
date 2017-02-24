package pl.com.bottega.iphotostock.sales.presentation;


import pl.com.bottega.iphotostock.sales.application.PurchaseProcess;
import pl.com.bottega.iphotostock.sales.model.client.CantAffordException;
import pl.com.bottega.iphotostock.sales.model.purchase.Offer;
import pl.com.bottega.iphotostock.sales.model.purchase.OfferMismatchException;
import pl.com.bottega.iphotostock.sales.model.product.Product;

import java.util.Scanner;

public class OfferScreen {
    private Scanner scanner;
    private LoginScreen loginScreen;
    private final PurchaseProcess purchaseProcess;

    public OfferScreen(Scanner scanner, LoginScreen loginScreen, PurchaseProcess purchaseProcess) {
        this.scanner = scanner;
        this.loginScreen = loginScreen;
        this.purchaseProcess = purchaseProcess;
    }

    public void print() {
        String reservationNumber = purchaseProcess.getReservation(loginScreen.getAuthenticatedClientNumber());
        try {
            Offer offer = purchaseProcess.calculateOffer(reservationNumber);
            printOffer(offer);
            askForConfirmation(offer, reservationNumber);
        } catch (IllegalStateException ex) {
            System.out.println("Nie ma aktywnych produktów w rezerwacji. Dodaj produkty.");
        }
    }

    private void askForConfirmation(Offer offer, String reservationNumber) {
        System.out.println("Czy chcesz dokonać zakupu? (t/n)");
        String answer = scanner.nextLine();
        if (answer.equals("t")) {
            try {
                purchaseProcess.confirm(reservationNumber, offer);
                System.out.println("Gratulujemy właściwej decyzji!!!");
            } catch (CantAffordException ex) {
                System.out.println("Niestety nie masz wystarczających środków na koncie.");
            } catch (OfferMismatchException ex) {
                System.out.println("Za późno, oferta wygasła.");
            }
        } else {
            System.out.println("Szkoda, może następnym razem.");
        }
    }

    private void printOffer(Offer offer) {
        System.out.println("Oferta specjalnie dla Ciebie: ");
        int i = 0;
        for (Product product : offer.getItems()) {
            System.out.println(String.format("%d. %s,", i++, product.getName()));
        }
        System.out.println(String.format("Za jedynie: %s", offer.getTotalCost()));
    }
}
