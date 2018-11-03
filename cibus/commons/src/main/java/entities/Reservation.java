package entities;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@Entity
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long tableOfTwo;

    private Long tableOfFour;

    @Column(name = "reservationTime")
    private LocalTime dt;

    @ManyToOne
    @JoinColumn(name = "restaurant")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "consumer")
    private Consumer consumer;

    private Long totalPeople;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    public Reservation(LocalTime dt, Restaurant restaurant, Consumer consumer, Long totalPeople, ReservationStatus reservationStatus) {
        this.dt = dt;
        this.restaurant = restaurant;
        this.consumer = consumer;
        this.totalPeople = totalPeople;
        this.reservationStatus = reservationStatus;
    }
}
//necesito guardar en algun lado el email del usuario o restaurant
//Cuando el consumer elija el restaurant que quiere reservar se usaria un SetRestaurantId (idem para consumer)
//Cuando entre el consumer el email (osea el id) se tiene que guardar en algun lado para luego poner setId