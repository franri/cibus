package labtic.rmi;



import entities.Food;
import entities.Neighbourhood;
import entities.Restaurant;
import labtic.services.FoodService;
import labtic.services.NeighbourhoodService;
import labtic.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BackendServiceImp implements BackendService {

    @Autowired
    NeighbourhoodService ns;

    @Autowired
    FoodService fs;

    @Autowired
    RestaurantService rs;

    public List<Neighbourhood> getListaBarrios() throws RemoteException {
        List<Neighbourhood> nList = new ArrayList<>();
        List<Neighbourhood> lista = ns.findAllNeighbourhoods();
        System.out.println("JEJE");
        lista.forEach(neighbourhood -> {
            nList.add(neighbourhood);
        });

        return nList;
    }

    public List<Food> getListaComidas() throws RemoteException {
        List<Food> fList = new ArrayList<>();
        fs.findAllFoodTypes().forEach(food -> {
            fList.add(food);
        });
        return fList;
    }


    public List<Restaurant> filtrarRestaurants(String name, List<Food> foods, List<Neighbourhood> neighbourhoods, Integer seatsToReserve) throws RemoteException {
        return rs.findWithFilters(name, foods, neighbourhoods, seatsToReserve);
    }

}
