package edu.upenn.cis.cis455.webserver.exception;

/**
 * @author brishi
 */
public class EmptyRequestException extends BadRequestException {

    public EmptyRequestException() {
        super();
    }

    public EmptyRequestException(String message) {
        super(message);
    }

    public EmptyRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyRequestException(Throwable cause) {
        super(cause);
    }
}
