package pl.com.bottega.iphotostock.sales.infrastructure;


import pl.com.bottega.iphotostock.sales.model.*;


import java.util.HashMap;
import java.util.Map;

public class InMemmoryReservationRepository implements ReservationRepository {

    private static final Map<String, Reservation> REPOSITORY = new HashMap<>();

    @Override
    public void put(Reservation reservation) {
        REPOSITORY.put(reservation.getNumber(), reservation);
    }

    @Override
    public Reservation get(String reservationNumber) {
        return REPOSITORY.get(reservationNumber);
    }

    @Override
    public Reservation getActiveReservationForClient(String clientNumber) {
        //pętal po rezerw i spr czy jest taka dla zadaneoo nru klienta, jesli nie ma - null. rezerw z hashmapy
        for (Reservation reservation : REPOSITORY.values()) {
            if (reservation.isOwnedBy(clientNumber))
                return reservation;
        }
        return null;
    }
}
