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
    ReservationService resServ;

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
        //System.out.println("JEJE");
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
    public List<Restaurant> filtrarRestaurantsSinSeleccionarComida(String name, List<Neighbourhood> neighbourhoods, Long seatsToReserve) throws RemoteException {
        return rs.findWithFiltersWithoutFoods(name,neighbourhoods,seatsToReserve);
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
    public void refuseReservation(Reservation reservation) throws RemoteException {
        resServ.refuseReservation(reservation);
    }

    @Override
    public void acceptReservation(Reservation reservation) throws RemoteException {
        resServ.acceptReservation(reservation);
    }

  @Override
    public void completeReservation(Reservation reservation) throws RemoteException {
        resServ.completeReservation(reservation);
    }

    @Override
    public List<Reservation> findAccResOf(Restaurant restaurant) throws RemoteException {
        return resServ.findAccResOf(restaurant);
    }

    @Override
    public List<Reservation> findPendResOf(Restaurant restaurant) throws RemoteException {
        return resServ.findPendResOf(restaurant);
    }

    @Override
    public void reduceFree(Restaurant restaurant, Long totalPeople, Long tablesOfTwo, Long tablesOfFour) throws RemoteException {
        restaurant.setFreePlaces(restaurant.getFreePlaces()-totalPeople);
        restaurant.setTableForTwo(restaurant.getTableForTwo()-tablesOfTwo);
        restaurant.setTableForFour(restaurant.getTableForFour()-tablesOfFour);
        rs.save(restaurant);
    }

    @Override
    public void cobrar(Restaurant restaurant) throws RemoteException {
        Long cobrosAlRestaurant = restaurant.getCobroDeServicio();
        restaurant.setCobroDeServicio(cobrosAlRestaurant+20);
        rs.save(restaurant);
    }

    @Override
    public List<Reservation> getListOfReservationsFromConsumer(Consumer consumer) throws RemoteException {
        List<Reservation> exitList = resServ.findReservationsOfConsumer(consumer);
        return exitList;
    }

    @Override
    public List<Reservation> getListOfReservationsByRestaurant(Restaurant restaurant) throws RemoteException {
        return resServ.findReservationByRestaurant(restaurant);
    }

    @Override
    public List<Restaurant> getRestaurants() throws RemoteException {
        return rs.findAllRestaurants();
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
        resServ.save(reservation);
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
