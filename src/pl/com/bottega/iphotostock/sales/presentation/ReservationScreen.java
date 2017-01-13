package pl.com.bottega.iphotostock.sales.presentation;


import pl.com.bottega.iphotostock.sales.application.PurchaseProcess;
import pl.com.bottega.iphotostock.sales.model.ProductNotAvailableException;

import java.util.Scanner;

public class ReservationScreen {
    private Scanner scanner;
    private LoginScreen loginScreen;
    private PurchaseProcess purchaseProcess;

    public ReservationScreen(Scanner scanner, LoginScreen loginScreen, PurchaseProcess purchaseProcess) {
        this.scanner = scanner;
        this.loginScreen = loginScreen;
        this.purchaseProcess = purchaseProcess;
    }

    public void print() {
        while (true) {
            System.out.println("Podaj numer produktu do rezerwacji: ");
            String productNumber = scanner.nextLine();
            try {
                String clientNumber = loginScreen.getAuthenticatedClientNumber();
                String reservationNumber = purchaseProcess.getReservation(clientNumber);
                purchaseProcess.add(reservationNumber, productNumber);
                System.out.println(String.format("Produkt %s został dodany do rezerwacji %s.", productNumber, reservationNumber));
                return;
            } catch (ProductNotAvailableException ex) {
                System.out.println(String.format("Przepraszamy, produkt %s jest niedostępny", productNumber));
            } catch (IllegalArgumentException ex) {
                System.out.println("Nieprawidłow numer produktu.");
            }
        }
    }
}
