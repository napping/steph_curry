package edu.upenn.cis.cis455.webserver.exception;

/**
 * @author brishi
 */
public class InvalidHttpRequestException extends BadRequestException {
    public InvalidHttpRequestException() {
        super();
    }

    public InvalidHttpRequestException(String message) {
        super(message);
    }

    public InvalidHttpRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidHttpRequestException(Throwable cause) {
        super(cause);
    }
}
