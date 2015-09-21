package edu.upenn.cis.cis455.webserver.context;

import edu.upenn.cis.cis455.webserver.enumeration.HttpStatusCodeType;

import java.io.BufferedOutputStream;

/**
 * @author brishi
 */
public class HttpResponseContext {

    BufferedOutputStream response;
    HttpStatusCodeType statusCode;

    // Defaults
    public HttpResponseContext () {
        this.statusCode = HttpStatusCodeType._200;
    }

    /******************************** GETTERS ******************************/
    public BufferedOutputStream getResponse() {
        return response;
    }

    public HttpStatusCodeType getStatusCode() {
        return statusCode;
    }

    /******************************** SETTERS ******************************/

    public void setResponse(BufferedOutputStream response) {
        this.response = response;
    }

    public void setStatusCode(HttpStatusCodeType statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isSuccess() {
        return this.statusCode == HttpStatusCodeType._200;
    }
}
