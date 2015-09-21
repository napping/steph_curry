package edu.upenn.cis.cis455.webserver.context;

import edu.upenn.cis.cis455.webserver.enumeration.*;

import java.util.Date;

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
    private String request;
    private HttpMethodType header;
    private BasicFileType contentType;
    private SpecialUrlType specialUrlType;
    private HttpVersionType httpVersionType;
    private ConnectionType connectionType;
    private String userAgent;
    private String host;
    private Date ifModifiedSince;

    private Date ifUnmodifiedSince;

    // Defaults
    public HttpRequestContext() {
        request = "/";
        header = HttpMethodType.GET;
        contentType = BasicFileType.ALL;
        specialUrlType = SpecialUrlType.NOT_SPECIAL;
        httpVersionType = HttpVersionType.v11;
        connectionType = ConnectionType.KEEP_ALIVE;
        // Not sure if this will matter in the assignment.
        userAgent = "";
        host = "";
        ifModifiedSince = null;
        ifUnmodifiedSince = null;
    }

    /******************************** GETTERS ******************************/
    public String getRequest() {
        return request;
    }

    public HttpMethodType getHeader() {
        return header;
    }

    public BasicFileType getContentType() {
        return contentType;
    }

    public SpecialUrlType getSpecialUrlType() {
        return specialUrlType;
    }

    public HttpVersionType getHttpVersionType() {
        return httpVersionType;
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

    public Date getIfModifiedSince() {
        return ifModifiedSince;
    }

    public Date getIfUnmodifiedSince() {
        return ifUnmodifiedSince;
    }

    /******************************** SETTERS ******************************/
    public void setRequest(String directoryString) {
        this.request = directoryString;
    }

    public void setHeader(HttpMethodType header) {
        this.header = header;
    }

    public void setContentType(BasicFileType contentType) {
        this.contentType = contentType;
    }

    public void setSpecialUrlType(SpecialUrlType specialUrlType) {
        this.specialUrlType = specialUrlType;
    }

    public void setHttpVersionType(HttpVersionType httpVersionType) {
        this.httpVersionType = httpVersionType;
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

    public void setIfModifiedSince(Date ifModifiedSince) {
        this.ifModifiedSince = ifModifiedSince;
    }

    public void setIfUnmodifiedSince(Date ifUnmodifiedSince) {
        this.ifUnmodifiedSince = ifUnmodifiedSince;
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
        sb.append(httpVersionType);
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
        sb.append("If-Modified-Since: ");
        sb.append(ifModifiedSince);
        sb.append("\n");
        sb.append("If-Unmodified-Since: ");
        sb.append(ifUnmodifiedSince);
        sb.append("\n");
        sb.append("\n");

        return sb.toString();
    }
}
