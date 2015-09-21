package edu.upenn.cis.cis455.webserver.exception;

/**
 * @author brishi
 */
public class InvalidDateException extends BadRequestException {

    public InvalidDateException() {
        super();
    }

    public InvalidDateException(String message) {
        super(message);
    }

    public InvalidDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDateException(Throwable cause) {
        super(cause);
    }
}

