package edu.upenn.cis.cis455.webserver.exception;

/**
 * @author brishi
 */
public class UnsupportedHttpMethodException extends BadRequestException {
    public UnsupportedHttpMethodException() {
        super();
    }

    public UnsupportedHttpMethodException(String message) {
        super(message);
    }

    public UnsupportedHttpMethodException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedHttpMethodException(Throwable cause) {
        super(cause);
    }
}
