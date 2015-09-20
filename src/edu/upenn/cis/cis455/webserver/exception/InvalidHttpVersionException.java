package edu.upenn.cis.cis455.webserver.exception;

/**
 * @author brishi
 */
public class InvalidHttpVersionException extends Exception {
    public InvalidHttpVersionException() {
        super();
    }

    public InvalidHttpVersionException(String message) {
        super(message);
    }

    public InvalidHttpVersionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidHttpVersionException(Throwable cause) {
        super(cause);
    }
}
