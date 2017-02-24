package pl.com.bottega.iphotostock.sales.model.purchase;


public interface ReservationRepository {

    void put(Reservation reservation);

    Reservation get(String reservationNumber);

    Reservation getActiveReservationForClient(String clientNumber);

}
