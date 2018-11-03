package labtic.services;

import entities.Reservation;
import entities.ReservationStatus;
import entities.Restaurant;
import labtic.database.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository reservationRepository;

    public void save(Reservation reservation){
        reservationRepository.save(reservation);
    }

    public void refuseReservation(Reservation reservation){
        reservation.setReservationStatus(ReservationStatus.DECLINED);
    }

    public void acceptReservation(Reservation reservation) {
        reservation.setReservationStatus(ReservationStatus.ACCEPTED);
    }

    public List<Reservation> findAccResOf(Restaurant restaurant){
       List<Reservation> acceptedByRestaurant = reservationRepository.findAllByRestaurantAndReservationStatus_Accepted(restaurant);
       return acceptedByRestaurant;
    }

    public List<Reservation> findPendResOf(Restaurant restaurant){
        List<Reservation> pendingByRestaurant = reservationRepository.findAllByRestaurantAndReservationStatus_Pending(restaurant);
        return pendingByRestaurant;
    }
}
