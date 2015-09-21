package edu.upenn.cis.cis455.webserver.context;

import edu.upenn.cis.cis455.webserver.enumeration.*;

/**
 * @author brishi
 *
 * Following the Context Object Design pattern.
 * Encapsulates information about an HTTP request into a general, portable,
 * and extensible form.
 *
 * More information:
 *      http://www.cs.wustl.edu/~schmidt/PDF/Context-Object-Pattern.pdf
 *
 */
public class HttpRequestContext {
    String request;
    HttpMethodType header;
    BasicMimeType contentType;
    SpecialUrlType specialUrlType;
    HttpVersion httpVersion;
    ConnectionType connectionType;
    String userAgent;
    String host;

    // Defaults
    public HttpRequestContext() {
        request = "/";
        header = HttpMethodType.GET;
        contentType = BasicMimeType.ALL;
        specialUrlType = SpecialUrlType.NOT_SPECIAL;
        httpVersion = HttpVersion.v11;
        connectionType = ConnectionType.KEEP_ALIVE;
        // Not sure if this will matter in the assignment.
        userAgent = "";
        host = "";
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

    public ConnectionType getConnectionType() {
        return connectionType;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getHost() {
        return host;
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

    public void setConnectionType(ConnectionType connectionType) {
        this.connectionType = connectionType;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setHost(String host) {
        this.host = host;
    }

    /******************************** OTHER ******************************/
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
        sb.append("Content-type: ");
        sb.append(contentType);
        sb.append("\n");
        sb.append("Special URL Type: ");
        sb.append(specialUrlType);
        sb.append("\n");
        sb.append("HTTP Version: ");
        sb.append(httpVersion);
        sb.append("\n");
        sb.append("Connection-type: ");
        sb.append(connectionType);
        sb.append("\n");
        sb.append("User-agent: ");
        sb.append(userAgent);
        sb.append("\n");
        sb.append("Host: ");
        sb.append(host);
        sb.append("\n");
        sb.append("\n");

        return sb.toString();
    }
}
