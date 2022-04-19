package eu.avidatech.exception;

public class WrongFormatException extends RuntimeException {

    private final String message;

    public WrongFormatException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
