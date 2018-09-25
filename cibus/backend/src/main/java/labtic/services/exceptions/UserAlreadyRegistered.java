package labtic.services.exceptions;

public class UserAlreadyRegistered extends Throwable {
    public UserAlreadyRegistered(String message) {
        super(message);
    }
}
