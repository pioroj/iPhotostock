package pl.com.bottega.iphotostock.sales.presentation;


import java.util.Scanner;

public class MainScreen {

    private final Scanner scanner;
    private final SearchScreen searchScreen;
    private final ReservationScreen reservationScreen;
    private final OfferScreen offerScreen;
    private final LightBoxScreen lightboxScreen;

    public MainScreen(Scanner scanner,
                      SearchScreen searchScreen,
                      ReservationScreen reservationScreen,
                      OfferScreen offerScreen,
                      LightBoxScreen lightboxScreen) {
        this.scanner = scanner;
        this.searchScreen = searchScreen;
        this.reservationScreen = reservationScreen;
        this.offerScreen = offerScreen;
        this.lightboxScreen = lightboxScreen;
    }

    public void print() {
        while (true) {
            printMenu();
            String command = getCommand();
            executeCommand(command);
        }
    }

    private void executeCommand(String command) {
        switch (command) {
            case "1" :
                searchScreen.print();
                break;
            case "2" :
                reservationScreen.print();
                break;
            case "3" :
                offerScreen.print();
                break;
            case "4":
                lightboxScreen.print();
                break;
            default:
                System.out.println("Nie rozumie");
        }
    }

    private void printMenu() {
        System.out.println("1. Wyszukaj produkty");
        System.out.println("2. Zarezerwuj produkt");
        System.out.println("3. Wygeneruj ofertę.");
        System.out.println("4. Zarządzaj lightboxami.");
    }


    private String getCommand() {
        System.out.print("Co chcesz zrobić: ");
        return scanner.nextLine();
    }
}
