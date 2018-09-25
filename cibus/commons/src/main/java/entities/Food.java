package entities;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "Food")
public class Food {
    @Id
    @NonNull
    @Column(name = "food_name", nullable = false)
    private String foodName;

    @ManyToMany(mappedBy = "foods")
    private transient List<Restaurant> list;
}
