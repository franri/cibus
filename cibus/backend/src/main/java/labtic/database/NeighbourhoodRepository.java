package labtic.database;

import entities.Neighbourhood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NeighbourhoodRepository extends JpaRepository<Neighbourhood, Long> {
}
