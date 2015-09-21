package edu.upenn.cis.cis455.webserver.exception;

/**
 * @author brishi
 */
public class ServerErrorException extends Exception {

    public ServerErrorException() {
        super();
    }

    public ServerErrorException(String message) {
        super(message);
    }

    public ServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerErrorException(Throwable cause) {
        super(cause);
    }
}
