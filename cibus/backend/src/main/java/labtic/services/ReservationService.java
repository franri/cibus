package labtic.services;

import entities.Reservation;
import labtic.database.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository reservationRepository;

    public void save(Reservation reservation){
        reservationRepository.save(reservation);
    }

}
