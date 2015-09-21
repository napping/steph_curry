package edu.upenn.cis.cis455.webserver.enumeration;

/**
 * @author brishi
 *
 * Only includes the "common" status codes.
 */
public enum HttpStatusCodeType {
    _200,
    _304, // If-Modified-Since fail
    _400,
    _404,
    _412,  // If-Unmodified-Since fail
    _500
}
