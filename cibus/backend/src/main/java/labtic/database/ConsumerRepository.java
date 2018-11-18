package labtic.database;


import entities.Consumer;
import entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumerRepository extends JpaRepository<Consumer, String> {
    Consumer findOneByEmail(String email);
    boolean existsConsumerByEmail(String email);
}
