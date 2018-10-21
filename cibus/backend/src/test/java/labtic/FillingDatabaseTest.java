package labtic;


import entities.Food;
import entities.Neighbourhood;
import entities.Restaurant;
import labtic.services.FoodService;
import labtic.services.NeighbourhoodService;
import labtic.services.RestaurantService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FillingDatabaseTest {

    @Autowired
    FoodService fs;

    @Autowired
    NeighbourhoodService ns;

    @Autowired
    RestaurantService rs;

    @Before
    public void filling(){

        Food f1 = new Food("Papas");
        Food f2 = new Food("Asado");
        Food f3 = new Food("Hamburguesa");
        fs.save(f1);
        fs.save(f2);
        fs.save(f3);

        Neighbourhood n1 = new Neighbourhood("Ciudad Vieja");
        Neighbourhood n2 = new Neighbourhood("Centro");
        Neighbourhood n3 = new Neighbourhood("Pocitos");
        ns.save(n1);
        ns.save(n2);
        ns.save(n3);

        Restaurant r1 = new Restaurant("pepe@gmail.com", "pepe", "Restaurante de Pepe", "1234", 50L, n1);
        r1.setFoods(new ArrayList<Food>(Arrays.asList(f1,f2,f3)));

        Restaurant r2 = new Restaurant("maria@gmail.com", "maria", "Restaurante de Maria", "1111", 50L, n1);
        r2.setFoods(new ArrayList<Food>(Arrays.asList(f1)));

        Restaurant r3 = new Restaurant("juan@gmail.com","juan", "Restaurante de Juan", "4321", 50L, n2);
        r3.setFoods(new ArrayList<Food>(Arrays.asList(f1,f3)));

        rs.save(r1);
        rs.save(r2);
        rs.save(r3);

    }

    @Test
    public void load(){}

}
