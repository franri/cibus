package entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
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

    private Long tableForFour;

    private Long tableForTwo;
    
    private boolean canBeShown; //default = false

    private LocalDate openingHour;

    private LocalDate closingHour;

    @Column(precision = 2, scale = 1)
    private BigDecimal rating = new BigDecimal(0);

    @Column(precision = 5, scale = 2)
    private BigDecimal avgPrice = new BigDecimal(0);

    @ManyToOne
    @JoinColumn(name = "neighbourhood", nullable = false)
    @NonNull
    private Neighbourhood neighbourhood;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Food> foods = new ArrayList<>();

    public Restaurant(String email, String password, String name, String RUT, Long maxCapacity,
                      Neighbourhood neighbourhood) {
        super(email,password);
        this.name = name;
        this.RUT = RUT;
        this.maxCapacity = maxCapacity;
        this.neighbourhood = neighbourhood;
    }
}


