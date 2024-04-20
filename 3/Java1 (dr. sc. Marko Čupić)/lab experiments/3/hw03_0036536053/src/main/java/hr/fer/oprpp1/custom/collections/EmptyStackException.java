package hr.fer.oprpp1.custom.collections;

public class EmptyStackException extends RuntimeException {

    public EmptyStackException() {
    }

    public EmptyStackException(String message) {
        super(message);
    }

    public EmptyStackException(Throwable cause) {
        super(cause);
    }

    public EmptyStackException(String message, Throwable cause) {
        super(message, cause);
    }
}
