package labtic.database;

import entities.Food;
import entities.Neighbourhood;
import entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant,String> {

    List<Restaurant> findByNameContainingAndFoodsAndNeighbourhoodInAndMaxCapacityGreaterThanEqual(String name, List<Food> foods, List<Neighbourhood> neighbourhood, Integer maxCapacity);
    Restaurant findOneByEmail(String email);
}
