package edu.upenn.cis.cis455.webserver.thread;

import edu.upenn.cis.cis455.webserver.blockingqueue.BlockingQueue;
import edu.upenn.cis.cis455.webserver.context.HttpRequestContext;
import edu.upenn.cis.cis455.webserver.context.HttpResponseContext;
import edu.upenn.cis.cis455.webserver.enumeration.BasicFileType;
import edu.upenn.cis.cis455.webserver.enumeration.HttpMethodType;
import edu.upenn.cis.cis455.webserver.enumeration.HttpStatusCodeType;
import edu.upenn.cis.cis455.webserver.exception.*;
import edu.upenn.cis.cis455.webserver.utils.ContextParser;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.Date;

import static edu.upenn.cis.cis455.webserver.utils.Miscellaneous.getServerTime;

/**
 * @author brishi
 *
 * The worker thread that drives the server.
 * From the blocking queue, these workers dequeue request sockets and attempt
 * to parse and response to the request.
 */
public class QueueWorker implements Runnable {
    final Logger logger = Logger.getLogger(QueueWorker.class);

    private BlockingQueue<Socket> queue;
    private boolean RUNNING;
    private ThreadPool parent;
    private String rootDirectory;

    public QueueWorker(BlockingQueue queue, ThreadPool parent) {
        this.queue = queue;
        this.RUNNING = true;
        this.parent = parent;
        this.rootDirectory = parent.getRootDirectory();
    }

    @Override
    public void run() {
        while (this.RUNNING) {
            try {
                Socket request = queue.dequeue();

                this.handleRequest(request);
                request.close();

            } catch (InterruptedException e) {
                logger.debug("InterruptedException thrown by worker. " +
                        "Error: " + e.getMessage());

            } catch (IOException e) {
                logger.debug("IOException thrown by worker. Error: " +
                        e.getMessage());
            }
        }
    }

    private void handleRequest(Socket socket) throws IOException {
        BufferedReader requestReader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

        HttpRequestContext request = new HttpRequestContext();
        HttpResponseContext response = new HttpResponseContext();

        logger.debug("HANDLING REQUEST");
        try {
            request = ContextParser.parseIntoContext(
                    requestReader);

        // Is there a better way..? TODO
        } catch (BadRequestException e) {
            response.setStatusCode(HttpStatusCodeType._400);
        }

        PrintStream out = new PrintStream(socket.getOutputStream());

        File f = null;
        switch (request.getSpecialUrlType()) {
            case NOT_SPECIAL:
                try {
                    f = new File(this.rootDirectory + request.getRequest());

                    if (f == null) {
                        logger.debug("FILE WAS NULL");
                        response.setStatusCode(HttpStatusCodeType._404);
                        outputResponse(out, request, response, f);
                        break;
                    }

                    handleModified(f, request, response);
                    handleDirectory(f, request);

                    outputResponse(out, request, response, f);

                } catch (FileNotFoundException e) {
                    logger.debug("FILE NOT FOUND");
                    response.setStatusCode(HttpStatusCodeType._404);
                    outputResponse(out, request, response, f);
                    // Dangerous, passing in a null fileIn object.
                }
                break;

            case CONTROL:
                // TODO
                break;

            case DESTORY:
                // TODO
                break;
        }

        requestReader.close();
        out.close();
        logger.debug("Done with handling request.");
    }

    private void handleModified(File f,
                                HttpRequestContext request,
                                HttpResponseContext response)
    {

        Date lastModified = request.getIfModifiedSince();
        if (lastModified != null) {
            if (!verifyLastModified(f, lastModified)) {
                response.setStatusCode(HttpStatusCodeType._304);
            }
        }

        Date lastUnmodified = request.getIfUnmodifiedSince();
        if (lastUnmodified != null) {
            if (verifyLastUnmodified(f, lastUnmodified)) {
                response.setStatusCode(HttpStatusCodeType._412);
            }
        }
    }

    private boolean verifyLastModified(File f, Date lastModified) {
        return f.lastModified() > lastModified.getTime();
    }

    private boolean verifyLastUnmodified(File f, Date lastModified) {
        return f.lastModified() < lastModified.getTime();
    }

    private void handleDirectory(File f,
                                 HttpRequestContext request) {
        if (f.isDirectory()) {
            request.setContentType(BasicFileType.DIRECTORY);
        }
    }

    // Not used.
    private boolean verifyValidFile(File f) {
        if (!f.exists()) {
            return false;
        }

        boolean valid = false;

        try {
            if (f.getCanonicalPath().startsWith(this.rootDirectory)) {
                valid = true;
            } else {
                logger.debug("Canonical path check fails!");
            }
        } catch (IOException e) {
            logger.debug("Canonical path check fails!");
            return false;
        }

        return valid;

    }

    private void outputResponse(PrintStream out,
                                HttpRequestContext request,
                                HttpResponseContext response,
                                File f) throws IOException
    {
        outputResponseHeader(out, response);

        if (response.isSuccess()) {
            // Directory outputs text/html
            outputContentTypeLine(out, request);

            if (request.getContentType() == BasicFileType.DIRECTORY) {
                outputDirectoryHtml(out, request, response, f);

            } else {
                FileInputStream fileIn = new FileInputStream(f);
                byte[] data = generateFileOutput(
                        out, request, response, fileIn);

                outputContentLengthLine(out, data);
                if (request.getHeader() == HttpMethodType.GET) {
                    outputResponseBody(out, response, data);
                }

            }
        } else {
            logger.debug("Was not a 200, basically.");
            // Error
            if (response.getStatusCode() == HttpStatusCodeType._404) {
                // TODO Handle
            } else {
                outputConnectionClose(out);
            }
        }
    }

    private void outputResponseHeader(PrintStream out,
                                      HttpResponseContext response) {
        logger.debug(generateHttpLine(response));
        logger.debug(generateDateLine(response));
        out.println(generateHttpLine(response));
        out.println(generateDateLine(response));

    }

    private void outputDirectoryHtml(PrintStream out,
                                     HttpRequestContext request,
                                     HttpResponseContext response,
                                     File f)
    {
        // TODO Handle content-length?
        out.println("");

        StringBuilder sb = new StringBuilder();
        sb.append("<html><body>");
        sb.append("<h1>The path ");
        sb.append("<strong>");
        sb.append(request.getRequest());
        sb.append("</strong>");
        sb.append(" is a directory.  Click below to navigate:<br/>");
        sb.append("</h1>");

        if (!f.getPath().equals((new File(this.rootDirectory)).getPath())) {
            sb.append("<h5>");
            sb.append("<a href='");

            String parentDir = ensureSlash(
                    f.getParentFile().getPath().substring(
                    this.rootDirectory.length() - 1));

            sb.append(parentDir);
            sb.append("'>Parent</a>");
            sb.append("</h5>");
        }

        String requestRoot = ensureSlash(request.getRequest());

        String childPath;
        File child;
        for (String s : f.list()) {
            childPath = requestRoot + s;
            child = new File(
                    this.rootDirectory + childPath);
            logger.debug(child.getPath());

            sb.append("<h5>");
            if (child.isDirectory()) {
                sb.append("<a href='");
                sb.append(childPath);
                sb.append("'>");
                sb.append(s);
                sb.append("/</a>");

            } else {
                sb.append("<a href='");
                sb.append(childPath);
                sb.append("'>");
                sb.append(s);
                sb.append("</a>");
            }
            sb.append("</h5>");
        }

        sb.append("</body></html>");

        out.println(sb.toString());
    }

    private String generateHttpLine(HttpResponseContext response) {
        String line = "HTTP/1.1 ";
        switch (response.getStatusCode()) {
            case _200:
                line += "200 OK";
                break;

            case _304:
                line += "304 Not Modified";
                break;

            case _400:
                line += "400 Bad Request";
                break;

            case _404:
                line += "404 Not Found";
                break;

            case _412:
                line += "412 Precondition Failed";
                break;

            case _500:
                line += "500 Internal Server Error";
                break;

            default:
                logger.debug("Should not be reached: 24");
                line += "500 Internal Server Error";

        }
        return line;
    }

    private String generateDateLine(HttpResponseContext response) {
        return "Date: " + getServerTime();
    }

    private void outputContentTypeLine(PrintStream out,
                                       HttpRequestContext request) {
        String line = "Content-Type: ";
        switch (request.getContentType()) {
            case JPG:
                line += "image/jpeg";
                break;

            case GIF:
                line += "image/gif";
                break;

            case PNG:
                line += "image/png";
                break;

            case TXT:
                line += "text/plain";
                break;

            case HTML:
                line += "text/html";
                break;

            case DIRECTORY:
                line += "text/html";
                break;

            default:
                logger.debug("Unrecognized content type!" + request.toString());
                line += "text/plain";
                // Should probably error
        }
        logger.debug(line);
        out.println(line);
    }

    private byte[] generateFileOutput(PrintStream out,
                                      HttpRequestContext request,
                                      HttpResponseContext response,
                                      FileInputStream fileIn)
            throws IOException
    {
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        int dataByte;
        while ((dataByte = fileIn.read()) != -1) {
            byteArrayOut.write(dataByte);
        }

        return byteArrayOut.toByteArray();
    }

    private void outputContentLengthLine(PrintStream out,
                                         byte[] data)
    {
        logger.debug("Content-Length: " + data.length);
        out.println("Content-Length: " + data.length);
    }

    private void outputResponseBody(PrintStream out,
                                    HttpResponseContext response,
                                    byte[] data) throws IOException {
        out.println("");
        out.write(data);
    }

    private void outputConnectionClose(PrintStream out) {
        out.println("Connection: close");
    }

    private String ensureSlash(String dir) {

        if (dir.length() > 0) {
            return dir + (dir.charAt(dir.length() - 1) != '/' ? "/" : "");
        }
        else {
            return "/";
        }
    }

}
