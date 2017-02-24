package pl.com.bottega.iphotostock.sales.presentation;


import pl.com.bottega.iphotostock.sales.application.LightBoxManagement;
import pl.com.bottega.iphotostock.sales.model.lightbox.LightBox;
import pl.com.bottega.iphotostock.sales.model.product.Product;

import java.util.Collection;
import java.util.Scanner;

public class LightBoxScreen {

    private Scanner scanner;
    private LoginScreen loginScreen;
    private LightBoxManagement lightBoxManagement;
    private boolean exit;


    public LightBoxScreen(Scanner scanner, LoginScreen loginScreen, LightBoxManagement lightBoxManagement) {
        this.scanner = scanner;
        this.loginScreen = loginScreen;
        this.lightBoxManagement = lightBoxManagement;
    }

    public void print() {
        exit = false;
        while (!exit) {
            printMenu();
            String[] cmd = readCommand();
            executeCommand(cmd);
        }
    }

    private String[] readCommand() {
        return scanner.nextLine().split(" ");
    }

    private void executeCommand(String[] cmd) {
        if (cmd.length == 1) {
            if (cmd[0].equals("pokaz")) {
                showLightBoxes();
                return;
            }
            if (cmd[0].equals("powrot")) {
                exit = true;
                return;
            }
        } else if (cmd.length == 2) {
            if (cmd[0].equals("pokaz")) {
                showLightBox(cmd[1]);
                return;
            } else if (cmd[0].equals("rezerwuj")) {
                reserveLightBox(cmd[1]);
                return;
            }
        } else if (cmd.length == 3 && cmd[0].equals("dodaj")) {
            addToLightBox(cmd[1], cmd[2]);
            return;
        }
        System.out.println("Sorry nie rozumiem ;(");
    }

    private void reserveLightBox(String lightBoxName) {
        lightBoxManagement.reserve(loginScreen.getAuthenticatedClientNumber(), lightBoxName);
        System.out.println("Produkty zostały zarezerwowane.");
    }

    private void addToLightBox(String ligthBoxName, String productNumber) {
        try {
            lightBoxManagement.addProduct(
                    loginScreen.getAuthenticatedClientNumber(),
                    ligthBoxName,
                    productNumber
            );
            System.out.println(String.format("Produkt %s został dodany do light boxa %s.",
                    productNumber, ligthBoxName));
        } catch (Exception ex) {
            System.out.println(String.format("Nie udało się dodać produktu %s do light boxa %s. Komunikat błędu: %s",
                    productNumber, ligthBoxName,
                    ex.getMessage()));
        }
    }

    private void showLightBox(String lightBoxName) {
        LightBox lightBox = null;
        try {
            lightBox = lightBoxManagement.getLightBox(loginScreen.getAuthenticatedClientNumber(), lightBoxName);
        } catch (IllegalArgumentException ex) {
            System.out.println(String.format("Nie znaleziono light boxa %s", lightBoxName));
            return;
        }
        System.out.println(String.format("Zawartość light boxa %s", lightBoxName));
        int i = 1;
        for (Product product : lightBox) {
            System.out.println(String.format("%d. %s", i++, product.getName()));
        }
    }

    private void showLightBoxes() {
        Collection<String> lightBoxes = lightBoxManagement.getLightBoxNames(loginScreen.getAuthenticatedClientNumber());
        if (lightBoxes.size() == 0)
            System.out.println("Nie masz aktualnie żadnych light boxów");
        else {
            System.out.println("Twoje light boxy:");
            int i = 1;
            for (String s : lightBoxes) {
                System.out.println(String.format("%d. %s", i++, s));
            }
        }
    }

    private void printMenu() {
        System.out.println("pokaz -> powoduje wyswietlenie nazw wszystkich lighboxow\n" +
                "pokaz [nazwa lightboxa] -> powoduje wyswietlenie produktow znajdujacych sie w lighboxie o zadanej nazwie\n" +
                "dodaj [nazwa lightboxa] [nr produktu] -> powoduje dodanie do lightboxa o zadanej nazwie produktu o zadanym numerze\n" +
                "rezerwuj [nazwa lightboxa] -> dodaje do rezerwacji wszystkie produkty z lightboxa\n" +
                "powrot -> powoduje powrot do menu głównego aplikacji"
        );
    }
}
