package rmi;


import entities.Food;
import entities.Neighbourhood;
import entities.Restaurant;
import entities.User;
import exceptions.NoRestaurantFound;
import exceptions.NoUserFound;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface BackendService extends Remote {

    List<Neighbourhood> getListaBarrios() throws RemoteException;
    List<Food> getListaComidas() throws RemoteException;
    List<Restaurant> filtrarRestaurants(String name, List<Food> foods, List<Neighbourhood> neighbourhoods,
                                        Long seatsToReserve, Long size) throws RemoteException;
    User findUser(String email) throws RemoteException, NoUserFound;
    Restaurant findRestaurant(String email) throws RemoteException , NoRestaurantFound;
    void saveRestaurant(Restaurant restaurant) throws RemoteException;
    //NO EXPONER AL RESTAURANT: DEVOLVER UN POJO QUE TENGA  LOS ATRIBUTOS, GETTERS Y SETTERS

}
