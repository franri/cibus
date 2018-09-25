package labtic.services;

import labtic.database.FoodRepository;
import entities.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {
    @Autowired
    FoodRepository foodRepository;

    public List<Food> findAllFoodTypes(){
        return foodRepository.findAll();
    }
    public void save(Food food){foodRepository.save(food);}
}
