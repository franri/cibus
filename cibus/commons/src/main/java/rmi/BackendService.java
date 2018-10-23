package rmi;


import entities.*;
import exceptions.NoConsumerFound;
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
    User findUser(String email) throws NoUserFound, RemoteException;
    boolean existsConsumerByEmail(String email) throws RemoteException;
    Consumer findConsumer(String email) throws RemoteException, NoConsumerFound;
    Restaurant findRestaurant(String email) throws RemoteException , NoRestaurantFound;
    void saveNewUser(User user) throws RemoteException;
    void saveNewConsumer(Consumer consumer) throws RemoteException;
    void saveRestaurant(Restaurant restaurant) throws RemoteException;
    void saveAdmin(Admin admin) throws RemoteException;
    boolean existsByRut(String rut) throws RemoteException;
    //NO EXPONER AL RESTAURANT: DEVOLVER UN POJO QUE TENGA  LOS ATRIBUTOS, GETTERS Y SETTERS

}
