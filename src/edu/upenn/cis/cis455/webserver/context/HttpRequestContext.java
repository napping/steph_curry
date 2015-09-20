package edu.upenn.cis.cis455.webserver.context;

import edu.upenn.cis.cis455.webserver.enumeration.*;

/**
 * @author brishi
 */
public class HttpRequestContext {
    String request;
    HttpMethodType header;
    BasicMimeType contentType;
    SpecialUrlType specialUrlType;
    HttpVersion httpVersion;

    // Defaults
    public HttpRequestContext() {
        request = "/";
        header = HttpMethodType.GET;
        contentType = BasicMimeType.HTML;
        specialUrlType = SpecialUrlType.NOT_SPECIAL;
        httpVersion = HttpVersion.v11;
    }

    /******************************** GETTERS ******************************/
    public String getRequest() {
        return request;
    }

    public HttpMethodType getHeader() {
        return header;
    }

    public BasicMimeType getContentType() {
        return contentType;
    }

    public SpecialUrlType getSpecialUrlType() {
        return specialUrlType;
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    /******************************** SETTERS ******************************/
    public void setRequest(String directoryString) {
        this.request = directoryString;
    }

    public void setHeader(HttpMethodType header) {
        this.header = header;
    }

    public void setContentType(BasicMimeType contentType) {
        this.contentType = contentType;
    }

    public void setSpecialUrlType(SpecialUrlType specialUrlType) {
        this.specialUrlType = specialUrlType;
    }

    public void setHttpVersion(HttpVersion httpVersion) {
        this.httpVersion = httpVersion;
    }

}
