package entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "Restaurant")
//@DiscriminatorValue(value = "RESTAURANT")
public class Restaurant extends User{

    @Column(name = "name", unique=true, nullable = false)
    @NonNull
    private String name;

    @Column(name = "rut", nullable = false)
    @NonNull
    private String RUT;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private Long phoneNumber;

    @Column(name = "max_capacity", nullable = false)
    @NonNull
    private Integer maxCapacity;

    @ManyToOne
    @JoinColumn(name = "neighbourhood", nullable = false)
    @NonNull
    private Neighbourhood neighbourhood;

    @ManyToMany
    private List<Food> foods = new ArrayList<>();

    public Restaurant(String email, String username, String password, String name, String RUT, Integer maxCapacity, Neighbourhood neighbourhood) {
        super(email, username, password);
        this.name = name;
        this.RUT = RUT;
        this.maxCapacity = maxCapacity;
        this.neighbourhood = neighbourhood;
    }
}


