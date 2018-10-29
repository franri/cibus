package exceptions;

import java.io.Serializable;

public class NoUserFound extends Exception {
    private static final long serialVersionUID = 1L;
    public NoUserFound(String message) {
        super(message);
    }
}