package labtic.services.exceptions;

public class ConsumerAlreadyRegistered extends Exception{
    public ConsumerAlreadyRegistered(String message) {
        super(message);
    }
}