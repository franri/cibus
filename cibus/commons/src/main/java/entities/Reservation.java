package entities;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "reservationTime")
    private LocalTime dt;

    @OneToOne
    @JoinColumn(name = "restaurant")
    private Restaurant restaurant;

    @OneToOne
    @JoinColumn(name = "consumer")
    private Consumer consumer;

    @Column(name = "totalPeople")
    private int groupSize;

    @Column(name = "pending")
    private boolean pending;

    @Column(name = "state")
    private boolean reservationState;
}
//necesito guardar en algun lado el email del usuario o restaurant
//Cuando el consumer elija el restaurant que quiere reservar se usaria un SetRestaurantId (idem para consumer)
//Cuando entre el consumer el email (osea el id) se tiene que guardar en algun lado para luego poner setId