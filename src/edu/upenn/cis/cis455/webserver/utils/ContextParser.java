package edu.upenn.cis.cis455.webserver.utils;

import edu.upenn.cis.cis455.webserver.context.HttpRequestContext;
import edu.upenn.cis.cis455.webserver.enumeration.*;
import edu.upenn.cis.cis455.webserver.exception.EmptyRequestException;
import edu.upenn.cis.cis455.webserver.exception.InvalidHttpRequestException;
import edu.upenn.cis.cis455.webserver.exception.InvalidHttpVersionException;
import edu.upenn.cis.cis455.webserver.exception.UnsupportedHttpMethodException;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author brishi
 *
 * Class that contains all the parsing operations for this project.  Parses
 * input streams into context obejcts. Methods are static and self-sufficient
 * within this class.
 *
 */
public class ContextParser {
    final static Logger logger = Logger.getLogger(ContextParser.class);

    private static final String[] ILLEGAL_STRINGS = {".." };
    private static final char[] ILLEGAL_CHARACTERS = { '\n', '\r', '\t',
            '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':', '~' };


    public static HttpRequestContext parseIntoContext(BufferedReader reader)
            throws  IOException,
                    InvalidHttpVersionException,
                    InvalidHttpRequestException,
                    UnsupportedHttpMethodException,
                    EmptyRequestException
    {

        HttpRequestContext context = new HttpRequestContext();
        String statusLine = reader.readLine();
        if (statusLine == null) {
            throw new EmptyRequestException("Request is empty");
        }

        parseStatusLine(context, statusLine);

        parseRequestBody(context, reader);

        logger.debug("");
        logger.debug(context.toString());
        logger.debug("");
        return context;
    }

    private static void parseStatusLine(
            HttpRequestContext context, String statusLine)
            throws  UnsupportedHttpMethodException,
                    InvalidHttpRequestException,
                    InvalidHttpVersionException
    {

        String[] statusSplit = statusLine.split("\\s+");

        String methodType = statusSplit[0];
        if (methodType.equals("GET") || statusSplit.equals("HEAD")) {
            context.setHeader(HttpMethodType.valueOf(methodType));
        } else {
            throw new UnsupportedHttpMethodException("Server only accepts " +
                    "GET and HEAD requests.");
        }

        String request = statusSplit[1];
        if (validateRequest(request)) {
            context.setRequest(request);
            if (context.getHeader() == HttpMethodType.GET) {
                if (request.equals("/control")) {
                    context.setSpecialUrlType(SpecialUrlType.CONTROL);

                } else if (request.equals("/destroy")) {
                    context.setSpecialUrlType(SpecialUrlType.DESTORY);
                } else {
                    context.setContentType(ascertainContentType(request));
                }
            }

        } else {
            throw new InvalidHttpRequestException("Invalid request string. " +
                    "It may contain illegal character(s).");
        }

        String httpVersion = statusSplit[2];
        if (httpVersion.equals("HTTP/1.1")) {
            context.setHttpVersionType(HttpVersionType.v11);

        } else if (httpVersion.equals("HTTP/1.0")) {
            context.setHttpVersionType(HttpVersionType.v10);

        } else {
            throw new InvalidHttpVersionException("Server only accepts HTTP " +
                    "Version 1.1 and 1.0.");
        }
    }

    private static BasicMimeType ascertainContentType(String request) {
        int l = request.length();
        if (request.charAt(l - 1) == '/') {
            return BasicMimeType.DIRECTORY;
        }

        String[] split = request.split("\\.");
        if (split.length <= 1) {
            return BasicMimeType.DIRECTORY;
        }
        String extension = split[split.length - 1];
        BasicMimeType type;
        try {
            type = BasicMimeType.valueOf(extension.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.debug("Cannot match extension '" + extension + "' to a " +
                    "MIME type");
            type = BasicMimeType.ALL;
            // What error?
        }

        return type;
    }

    private static boolean validateRequest(String request) {
        for (String s : ILLEGAL_STRINGS) {
            if (request.contains(s)) {
                return false;
            }
        }

        for (char c : ILLEGAL_CHARACTERS) {
            if (request.indexOf(c) > -1) {
                return false;
            }
        }

        if (!request.contains("/")) {
            return false;
        }

        return true;
    }

    private static void parseRequestBody(
            HttpRequestContext context, BufferedReader reader)
            throws IOException {

        String line;
        int spaceIndex;
        String headerName;
        String value;
        while ((line = reader.readLine()).length() != 0) {
            spaceIndex = line.indexOf(":");
            if (spaceIndex < 0) {
                continue;
            }

            headerName = line.substring(0, spaceIndex).trim();
            value = line.substring(spaceIndex + 1).trim();

            switch (headerName.toLowerCase()) {
                case "user-agent":
                    context.setUserAgent(value);
                    break;

                case "host":
                    context.setHost(value);
                    break;

                case "accept":
                    // TODO content type was already set?
                    // context.setContentType(BasicMimeType.ALL);
                    break;

                case "connection":
                    if (value.equals("keep-alive")) {
                        context.setConnectionType(ConnectionType.KEEP_ALIVE);
                    } else if (value.equals("close")) {
                        context.setConnectionType(ConnectionType.CLOSE);
                    }
                    break;

                default:
                    logger.debug("Could not parse header: " + headerName + " " +
                            "and value: " + value + ".");
            }
        }
    }
}














