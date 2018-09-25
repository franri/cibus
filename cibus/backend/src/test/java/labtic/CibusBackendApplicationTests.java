package labtic;


import entities.Food;
import entities.Neighbourhood;
import entities.Restaurant;
import labtic.services.FoodService;
import labtic.services.NeighbourhoodService;
import labtic.services.RestaurantService;
import labtic.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.List;

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

	@Test
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

		Restaurant res1 = new Restaurant("pepe@gmail.com", "Pepe", "pepe123", "Pepe", "44", 44, barrio);
		res1.getFoods().add(papas);
		res1.getFoods().add(burguer);

		Restaurant res2 = new Restaurant("maria@gmail.com", "Maria", "maria123", "Maria", "99", 123, barrio2);
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

        List<Restaurant> filtrados = rs.findWithFilters("", foods, barrios, 3);
        String lista = "";
        for (Restaurant filtrado : filtrados) {
            lista = lista.concat(filtrado.getName());
        }
        System.out.println(lista);
	}

}
