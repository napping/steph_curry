package edu.upenn.cis.cis455.webserver.context;

import java.io.BufferedOutputStream;

/**
 * @author brishi
 */
public class HttpResponseContext {

    BufferedOutputStream response;
    public HttpResponseContext () {
    }

    public BufferedOutputStream getResponse() {
        return response;
    }

    public void setResponse(BufferedOutputStream response) {
        this.response = response;
    }
}
