package labtic.services;

import entities.Food;
import entities.Neighbourhood;
import entities.Restaurant;
import exceptions.NoRestaurantFound;
import labtic.database.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {
    @Autowired
    RestaurantRepository restaurantRepository;

    public void save(Restaurant restaurant){
        restaurantRepository.save(restaurant);
    }

    public List<Restaurant> findWithFilters(String name, List<Food> foods, List<Neighbourhood> neighbourhoods,
                                            Long maxCapacity, Long size){
        return restaurantRepository.findWithFilters(name, foods, neighbourhoods, maxCapacity, size);
    }

    public Restaurant findByEmail(String email) throws NoRestaurantFound {
        Restaurant restaurant = restaurantRepository.findOneByEmail(email);
        if(restaurant == null){
            throw new NoRestaurantFound(null);
        }
        return restaurant;
    }
}