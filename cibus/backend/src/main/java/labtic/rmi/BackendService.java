package labtic.rmi;


import entities.Food;
import entities.Neighbourhood;
import entities.Restaurant;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface BackendService extends Remote {

    List<Neighbourhood> getListaBarrios() throws RemoteException;
    List<Food> getListaComidas() throws RemoteException;
    List<Restaurant> filtrarRestaurants(String name, List<Food> foods, List<Neighbourhood> neighbourhoods, Integer seatsToReserve) throws RemoteException;

    //NO EXPONER AL RESTAURANT: DEVOLVER UN POJO QUE TENGA  LOS ATRIBUTOS, GETTERS Y SETTERS

}
