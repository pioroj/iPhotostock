package pl.com.bottega.iphotostock.sales.presentation;


import pl.com.bottega.iphotostock.sales.application.LightBoxManagement;
import pl.com.bottega.iphotostock.sales.model.LightBox;

import java.util.Collection;
import java.util.Scanner;

public class LightBoxScreen {

    private Scanner scanner;
    private LoginScreen loginScreen;
    private LightBoxManagement lightBoxManagement;


    public LightBoxScreen(Scanner scanner, LoginScreen loginScreen, LightBoxManagement lightBoxManagement) {
        this.scanner = scanner;
        this.loginScreen = loginScreen;
        this.lightBoxManagement = lightBoxManagement;
    }

    public void print() {
        System.out.println("Wpisz co chcesz zrobić:");
        System.out.println("pokaż -> powoduje wyświetlenie nazw wszystkich lightboxów.");
        System.out.println("pokaż [nazwa lightboxa]");
        System.out.println("dodaj [nazwa lightboxa] [nr produktu]");
        System.out.println("powrót");
        while (true) {
            String[] command = getQuery();
            executeCommand(command);
            if (command[0].toLowerCase().equals("powrót"))
                break;
        }
    }

    private void executeCommand(String[] command) {
        String customerNumber = loginScreen.getAuthenticatedClientNumber();
        if (command.length == 1 && command[0].toLowerCase().equals("pokaż")) {
            showAllLightBoxesOfClient(customerNumber);
        } else if (command[0].toLowerCase().equals("powrót")) {
            return;
        } else if (command.length == 2 && command[0].toLowerCase().equals("pokaż")) {
            getClientLightBox(command[1], customerNumber);
        } else if (command.length == 3 && command[0].toLowerCase().equals("dodaj")) {
            lightBoxManagement.addProduct(customerNumber, command[1], command[2]);
        }
    }

    private void getClientLightBox(String lightBoxName, String customerNumber) {
        LightBox lightBox = lightBoxManagement.getLightBox(customerNumber, lightBoxName);
        System.out.println(lightBox);
    }

    private void showAllLightBoxesOfClient(String customerNumber) {
        Collection<String> lightBoxNames = lightBoxManagement.getLightBoxNames(customerNumber);
        for (String str : lightBoxNames) {
            System.out.println(str);
        }
    }

    private String[] getQuery() {
        return scanner.nextLine().split(" ");
    }
}
