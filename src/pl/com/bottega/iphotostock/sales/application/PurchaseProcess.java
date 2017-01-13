package pl.com.bottega.iphotostock.sales.application;


import pl.com.bottega.iphotostock.sales.model.*;

import java.util.IllformedLocaleException;

public class PurchaseProcess {

    private ClientRepository clientRepository;
    private ReservationRepository reservationRepository;
    private ProductRepository productRepository;

    public PurchaseProcess(ClientRepository clientRepository, ReservationRepository reservationRepository, ProductRepository productRepository) {
        this.clientRepository = clientRepository;
        this.reservationRepository = reservationRepository;
        this.productRepository = productRepository;
    }

    public String getReservation(String clientNumber) {
        Client client = clientRepository.get(clientNumber);
        if(!(client.isActive()))
            throw new IllegalArgumentException(String.format("Client %s is not active", clientNumber));
        Reservation reservation = reservationRepository.getActiveReservationForClient(clientNumber);
        if (reservation == null) {
            reservation = new Reservation(client);
            reservationRepository.put(reservation);
        }
        return reservation.getNumber();
    }

    public void add(String reservationNumber, String productNumber) {
        Reservation reservation = reservationRepository.get(reservationNumber);
        if (reservation  == null)
            throw new IllegalArgumentException(String.format("Reservation %s does not exist.", reservationNumber));
        Product product = productRepository.get(productNumber);
        if (product == null)
            throw new IllegalArgumentException(String.format("Product %s does not exist.", productNumber));
        reservation.add(product);
        //reservationRepository.put(reservation); cieknąca abstrakcja
    }

    public Offer calculateOffer(String reservationNumber) {
        //pobrac z rep rezerw i wywowlac generate ofer i zwrocic i spr wczy istnieje rezerw. null - wyjatek ze rezerw o zadanym nr nie istnieje
        Reservation reservation = reservationRepository.get(reservationNumber);
        if (reservation == null) {
            throw new IllegalArgumentException(String.format("Reservation %s does not exist", reservationNumber));
        }
        return reservation.generateOffer();
    }
}
