package jrds.agent;

public class CollectException extends RuntimeException {

    public CollectException(String message, Throwable cause) {
        super(message, cause);
    }

    public CollectException(String message) {
        super(message);
    }

}
