package edu.upenn.cis.cis455.webserver.context;

import edu.upenn.cis.cis455.webserver.enumeration.*;

import java.io.BufferedOutputStream;

/**
 * @author brishi
 */
public class HttpRequestContext {
    String directoryString;
    HttpMethodType header;
    BasicMimeType contentType;
    SpecialUrlType specialUrlType;


    // Defaults
    public HttpRequestContext() {
        directoryString = "/";
        header = HttpMethodType.GET;
        contentType = BasicMimeType.HTML;
        specialUrlType = SpecialUrlType.NOT_SPECIAL;
    }

    public String getDirectoryString() {
        return directoryString;
    }

    public void setDirectoryString(String directoryString) {
        this.directoryString = directoryString;
    }

    public HttpMethodType getHeader() {
        return header;
    }

    public void setHeader(HttpMethodType header) {
        this.header = header;
    }

    public BasicMimeType getContentType() {
        return contentType;
    }

    public void setContentType(BasicMimeType contentType) {
        this.contentType = contentType;
    }

    public SpecialUrlType getSpecialUrlType() {
        return specialUrlType;
    }

    public void setSpecialUrlType(SpecialUrlType specialUrlType) {
        this.specialUrlType = specialUrlType;
    }

}
