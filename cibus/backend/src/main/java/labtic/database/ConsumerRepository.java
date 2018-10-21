package labtic.database;


import entities.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumerRepository extends JpaRepository<Consumer, String> {
    Consumer findOneByEmail(String email);
}
