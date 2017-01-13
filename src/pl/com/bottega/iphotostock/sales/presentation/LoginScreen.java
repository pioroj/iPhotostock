package pl.com.bottega.iphotostock.sales.presentation;


import pl.com.bottega.iphotostock.sales.application.AuthenticationProcess;
import pl.com.bottega.iphotostock.sales.model.Client;

import java.util.Scanner;

public class LoginScreen {

    private Scanner scanner;
    private AuthenticationProcess authenticationProcess;
    private Client client;

    public LoginScreen(Scanner scanner, AuthenticationProcess authenticationProcess) {
        this.scanner = scanner;
        this.authenticationProcess = authenticationProcess;
    }

    public void print() {
        while (true) {
            System.out.println("Podaj numer klienta: ");
            String clientNumber = scanner.nextLine();
            client = authenticationProcess.authenticate(clientNumber);
            if(client != null) {
                System.out.println(String.format("Witaj %s", client.getName()));
                return;
            }
            System.out.println("Nieprawidłowy numer klienta. Spróbuj ponownie.");
        }
    }

    public String getAuthenticatedClientNumber() {
        return client.getNumber();
    }

    public Client getClient() {
        return client;
    }
}
