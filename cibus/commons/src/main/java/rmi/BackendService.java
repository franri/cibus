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
    List<Restaurant> filtrarRestaurants(String name, List<Food> foods, List<Neighbourhood> neighbourhoods,Long seatsToReserve, Long size) throws RemoteException;
    List<Restaurant> filtrarRestaurantsSinSeleccionarComida(String name, List<Neighbourhood> neighbourhoods, Long seatsToReserve) throws  RemoteException;
    User findUser(String email) throws NoUserFound, RemoteException;
    boolean existsConsumerByEmail(String email) throws RemoteException;
    Consumer findConsumer(String email) throws RemoteException, NoConsumerFound;
    Restaurant findRestaurant(String email) throws RemoteException , NoRestaurantFound;
    void saveNewUser(User user) throws RemoteException;
    void saveReservation(Reservation reservation) throws RemoteException;
    void saveNewConsumer(Consumer consumer) throws RemoteException;
    void saveRestaurant(Restaurant restaurant) throws RemoteException;
    void saveAdmin(Admin admin) throws RemoteException;
    boolean existsByRut(String rut) throws RemoteException;
    void refuseReservation(Reservation reservation) throws RemoteException;
    void acceptReservation(Reservation reservation) throws RemoteException;
    void completeReservation(Reservation reservation) throws RemoteException;
    List<Reservation> findAccResOf(Restaurant restaurant) throws RemoteException;
    List<Reservation> findPendResOf(Restaurant restaurant) throws RemoteException;
    void reduceFree(Restaurant restaurant, Long totalPeople, Long tablesOfTwo, Long tablesOfFour) throws RemoteException;
    void cobrar(Restaurant restaurant) throws RemoteException;

    //NO EXPONER AL RESTAURANT: DEVOLVER UN POJO QUE TENGA  LOS ATRIBUTOS, GETTERS Y SETTERS

}
