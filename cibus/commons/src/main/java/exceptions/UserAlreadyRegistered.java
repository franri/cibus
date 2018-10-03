package exceptions;

public class UserAlreadyRegistered extends Throwable {
    public UserAlreadyRegistered(String message) {
        super(message);
    }
}
