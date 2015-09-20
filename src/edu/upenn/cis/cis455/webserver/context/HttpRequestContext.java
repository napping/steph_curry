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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Printing out HttpRequestContext...\n");
        sb.append("Request: ");
        sb.append(request);
        sb.append("\n");
        sb.append("Header: ");
        sb.append(header);
        sb.append("\n");
        sb.append("Content Type: ");
        sb.append(contentType);
        sb.append("\n");
        sb.append("Special URL Type: ");
        sb.append(specialUrlType);
        sb.append("\n");
        sb.append("HTTP Version: ");
        sb.append(httpVersion);
        sb.append("\n");
        sb.append("\n");

        return sb.toString();
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
