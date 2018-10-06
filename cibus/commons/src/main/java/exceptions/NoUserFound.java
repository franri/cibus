package exceptions;

public class NoUserFound extends Throwable {
    public NoUserFound(String message) {
        super(message);
    }
}