package labtic.services;

import entities.Consumer;
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
        reservationRepository.save(reservation);
    }

    public void acceptReservation(Reservation reservation) {
        reservation.setReservationStatus(ReservationStatus.ACCEPTED);
        reservationRepository.save(reservation);
    }

    public void completeReservation(Reservation reservation) {
        reservation.setReservationStatus(ReservationStatus.COMPLETED);
        reservationRepository.save(reservation);
    }


    public List<Reservation> findAccResOf(Restaurant restaurant){
        return reservationRepository.findAllByRestaurantAndReservationStatus(restaurant, ReservationStatus.ACCEPTED);
    }

    public List<Reservation> findPendResOf(Restaurant restaurant){
        return reservationRepository.findAllByRestaurantAndReservationStatus(restaurant, ReservationStatus.PENDING);
    }

    public List<Reservation> findReservationsOfConsumer(Consumer consumer){
        return reservationRepository.findAllByConsumer(consumer);
    }

    public List<Reservation> findReservationByRestaurant(Restaurant restaurant){
        return reservationRepository.findAllByRestaurant(restaurant);
    }

    public Reservation findReservationById(Long id){
        return reservationRepository.findReservationById(id);
    }
}
