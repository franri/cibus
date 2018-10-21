package exceptions;

public class PasswordsDontMatch extends Exception{
    public PasswordsDontMatch(String message) {
        super(message);
    }
}
