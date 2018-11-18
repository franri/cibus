package labtic.database;

import entities.Consumer;
import entities.Reservation;
import entities.ReservationStatus;
import entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    List<Reservation> findAll();

    List<Reservation> findAllById(Iterable<Long> longs);

    List<Reservation> findAllByReservationStatusAndRestaurant(ReservationStatus reservationStatus, Restaurant restaurant);

    List<Reservation> findAllByRestaurantAndReservationStatus(Restaurant restaurant, ReservationStatus reservationStatus);

    List<Reservation> findAllByConsumer(Consumer consumer);

    List<Reservation> findAllByRestaurant(Restaurant restaurant);
}
