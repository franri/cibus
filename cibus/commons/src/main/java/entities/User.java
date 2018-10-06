package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "DISCRIMINATOR",discriminatorType = DiscriminatorType.STRING)
//@DiscriminatorValue(value = "USER")
public class User{

    @Id
    @Column(name = "email", unique = true)
    protected String email;

    @Column(name = "username", unique = true)
    @NonNull
    protected String username;

    @NonNull
    protected String password;
}
