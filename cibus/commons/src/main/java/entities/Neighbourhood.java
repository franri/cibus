package entities;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "Neighbourhood")
public class Neighbourhood implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column
    @NonNull
    private String name;

}
