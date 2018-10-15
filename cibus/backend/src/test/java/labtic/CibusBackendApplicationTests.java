package labtic;


import entities.Food;
import entities.Neighbourhood;
import entities.Restaurant;
import exceptions.NoRestaurantFound;
import labtic.services.FoodService;
import labtic.services.NeighbourhoodService;
import labtic.services.RestaurantService;
import labtic.services.UserService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CibusBackendApplicationTests {

	@Autowired
	UserService us;

	@Autowired
	RestaurantService rs;

	@Autowired
	NeighbourhoodService ns;

	@Autowired
	FoodService fs;

	@Ignore
	public void contextLoads() {

		Neighbourhood barrio = new Neighbourhood("Pocitos");
		ns.save(barrio);
		Neighbourhood barrio2 = new Neighbourhood("Centro");
		ns.save(barrio2);
		Food papas = new Food("Papas");
		Food burguer = new Food("Hamburguesa");
		Food ensalada = new Food("Ensalada");
		Food fideos = new Food("Fideos");
		fs.save(papas);
		fs.save(burguer);
		fs.save(ensalada);
		fs.save(fideos);

		Restaurant res1 = new Restaurant("pepe@gmail.com", "Pepe", "pepe123", "Pepe", "44", 44L, barrio);
		res1.getFoods().add(papas);
		res1.getFoods().add(burguer);

		Restaurant res2 = new Restaurant("maria@gmail.com", "Maria", "maria123", "Maria", "99", 123L, barrio2);
		res2.getFoods().add(papas);
		res2.getFoods().add(ensalada);

		rs.save(res1);
		rs.save(res2);

		List<Food> foods = new ArrayList<>();
		foods.add(papas);
//		foods.add(burguer);
//		foods.add(ensalada);
//		foods.add(fideos);
//		ERROR CUANDO ELIGO DOS COMIDAS CUALESQUIERA
		List<Neighbourhood> barrios = new ArrayList<>();
		barrios.add(barrio);
		barrios.add(barrio2);

        List<Restaurant> filtrados = rs.findWithFilters("", foods, barrios, 3L, Long.valueOf(foods.size()));
        String lista = "";
        for (Restaurant filtrado : filtrados) {
            lista = lista.concat(filtrado.getName());
        }
        System.out.println(lista);
	}

	@Test
	public void checkIfFilterWorks() throws NoRestaurantFound {
		List<Food> foods = new ArrayList<>();
		foods.add(new Food("Asado"));
		List<Neighbourhood> neighbourhoods = ns.findAllNeighbourhoods();
		System.out.println(rs.findByEmail("pepe").getName());
		List<Restaurant> restaurants = rs.findWithFilters("", foods, neighbourhoods, 1L, Long.valueOf(foods.size()));

		assertEquals(3, restaurants.size());
	}

}
