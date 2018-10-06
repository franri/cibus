package exceptions;

public class NoRestaurantFound extends Exception {
    public NoRestaurantFound(String message){
        super(message);
    }
}
