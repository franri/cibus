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
    List<Restaurant> filtrarRestaurants(String nombre, List<Food> comidas, List<Neighbourhood> barrios, Integer lugaresReservados) throws RemoteException;

    //NO EXPONER AL RESTAURANT: DEVOLVER UN POJO QUE TENGA  LOS ATRIBUTOS, GETTERS Y SETTERS

}