package labtic.rmi;



import entities.*;
import exceptions.NoConsumerFound;
import exceptions.NoRestaurantFound;
import exceptions.NoUserFound;
import labtic.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rmi.BackendService;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BackendServiceImp implements BackendService {

    @Autowired
    AdminService as;

    @Autowired
    ReservationService bs;

    @Autowired
    NeighbourhoodService ns;

    @Autowired
    FoodService fs;

    @Autowired
    RestaurantService rs;

    @Autowired
    UserService us;

    @Autowired
    ConsumerService cs;

    public List<Neighbourhood> getListaBarrios(){
        List<Neighbourhood> nList = new ArrayList<>();
        List<Neighbourhood> lista = ns.findAllNeighbourhoods();
        System.out.println("JEJE");
        lista.forEach(neighbourhood -> {
            nList.add(neighbourhood);
        });

        return nList;
    }

    public List<Food> getListaComidas(){
        List<Food> fList = new ArrayList<>();
        fs.findAllFoodTypes().forEach(food -> {
            fList.add(food);
        });
        return fList;
    }


    public List<Restaurant> filtrarRestaurants(String name, List<Food> foods, List<Neighbourhood> neighbourhoods, Long seatsToReserve, Long size){
        return rs.findWithFilters(name, foods, neighbourhoods, seatsToReserve, size);
    }

    @Override
    public User findUser(String email) throws NoUserFound{
        return us.findByEmail(email);
    }

    @Override
    public boolean existsByRut(String rut){
        return rs.existsByRut(rut);
    }

    @Override
    public boolean existsConsumerByEmail(String email){
        return cs.existsByEmail(email);
    }

    @Override
    public Consumer findConsumer(String email) throws NoConsumerFound {
        return cs.findByEmail(email);
    }

    @Override
    public Restaurant findRestaurant(String email) throws NoRestaurantFound{
        return rs.findByEmail(email);
    }

    @Override
    public void saveRestaurant(Restaurant restaurant){
        rs.save(restaurant);
    }

    @Override
    public void saveNewUser(User user){
        us.save(user);
    }

    @Override
    public void saveReservation(Reservation reservation) throws RemoteException {
        bs.save(reservation);
    }

    @Override
    public void saveNewConsumer(Consumer consumer){
        cs.save(consumer);
    }

    @Override
    public void saveAdmin(Admin admin){
        as.save(admin);
    }
}
