package entities;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Food implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    Long id;

    @NonNull
    @Column(name = "name", nullable = false)
    private String name;

//    @ManyToMany(mappedBy = "foods")
//    private  List<Restaurant> list;
}
