package labtic.database;

import entities.Food;
import entities.Neighbourhood;
import entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, String> {

    @Query(value = "SELECT r2 FROM Restaurant r2 WHERE EXISTS (SELECT r, COUNT(r) FROM Restaurant r INNER JOIN r.foods rf WHERE rf IN :foods AND r2=r GROUP BY r HAVING COUNT(r) = :sizeList) AND r2.name LIKE CONCAT('%',:name,'%') AND r2.neighbourhood IN :neighbourhoods AND r2.freePlaces >= :required AND r2.canBeShown IS TRUE")
    List<Restaurant> findWithFilters(@Param("name") String name, @Param("foods") List<Food> foods, @Param("neighbourhoods") List<Neighbourhood> neighbourhoods, @Param("required") Long required, @Param("sizeList") Long size);

    @Query(value = "SELECT r FROM Restaurant r WHERE r.name LIKE CONCAT('%',:name,'%') AND r.neighbourhood IN :neighbourhoods AND r.freePlaces >= :required AND r.canBeShown = TRUE")
    List<Restaurant> findWithFiltersWithoutFoods(@Param("name") String name, @Param("neighbourhoods") List<Neighbourhood> neighbourhoods, @Param("required") Long required);

    Restaurant findOneByEmail(String email);

    boolean existsByRut(String rut);

    List<Restaurant> findAll();

}
