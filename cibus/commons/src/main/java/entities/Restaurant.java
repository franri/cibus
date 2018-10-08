package entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
//@DiscriminatorValue(value = "RESTAURANT")
public class Restaurant extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "name", unique=true, nullable = false)
    @NonNull
    private String name;

    @Column(name = "rut", nullable = false, unique = true)
    @NonNull
    private String RUT;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private Long phoneNumber;

    @NonNull
    private Long maxCapacity;


    @Column(name = "Table_for_four", nullable = false)
    @NonNull
    private Integer tableForFour;

    @Column(name = "Table_for_two", nullable = false)
    @NonNull
    private Integer tableForTwo;
                                                                                                                                                
    @ManyToOne
    @JoinColumn(name = "neighbourhood", nullable = false)
    @NonNull
    private Neighbourhood neighbourhood;

    @ManyToMany
    private List<Food> foods = new ArrayList<>();

    public Restaurant(String email, String username, String password, String name, String RUT, Long maxCapacity,
                      Neighbourhood neighbourhood) {
        super(email, username, password);
        this.name = name;
        this.RUT = RUT;
        this.maxCapacity = maxCapacity;
        this.neighbourhood = neighbourhood;
    }
}


