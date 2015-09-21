package edu.upenn.cis.cis455.webserver.thread;

import edu.upenn.cis.cis455.webserver.blockingqueue.BlockingQueue;
import edu.upenn.cis.cis455.webserver.context.HttpRequestContext;
import edu.upenn.cis.cis455.webserver.context.HttpResponseContext;
import edu.upenn.cis.cis455.webserver.enumeration.BasicFileType;
import edu.upenn.cis.cis455.webserver.enumeration.HttpMethodType;
import edu.upenn.cis.cis455.webserver.enumeration.HttpStatusCodeType;
import edu.upenn.cis.cis455.webserver.exception.*;
import edu.upenn.cis.cis455.webserver.utils.ContextParser;

// import edu.upenn.cis.cis455.webserver.res.control;

import edu.upenn.cis.cis455.webserver.utils.HtmlStrings;
import org.apache.log4j.Logger;
import org.apache.tools.ant.taskdefs.condition.Http;

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

    boolean RUNNING;
    boolean WAITING;

    private BlockingQueue<Socket> queue;
    private ThreadPool parent;
    private String rootDirectory;

    private HttpRequestContext request;
    private HttpResponseContext response;

    public QueueWorker(BlockingQueue queue, ThreadPool parent) {
        this.queue = queue;
        this.RUNNING = true;
        this.parent = parent;
        this.rootDirectory = parent.getRootDirectory();
        this.WAITING = true;
    }

    @Override
    public void run() {
        while (this.RUNNING) {
            try {
                Socket request = queue.dequeue();
                this.handleRequest(request);

                request.close();

            } catch (InterruptedException e) {
                logger.debug("Worker interrupted.");

            } catch (IOException e) {
                logger.debug("IOException thrown by worker. Error: " +
                        e.getMessage());
            }
        }
    }

    public void stopRunning() {
        this.RUNNING = false;
    }

    public HttpRequestContext getRequestContext() {
        return request;
    }

    public HttpResponseContext getResponseContext() {
        return response;
    }

    private void handleRequest(Socket socket) throws IOException {
        this.WAITING = false;
        BufferedReader requestReader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

        this.request = new HttpRequestContext();
        this.response = new HttpResponseContext();

        try {
            request = ContextParser.parseIntoContext(
                    requestReader);

        // Is there a better way..? TODO
        } catch (BadRequestException e) {
            response.setStatusCode(HttpStatusCodeType._400);
        }

        logger.debug("Handling request: " + request.getRequest());

        PrintStream out = new PrintStream(socket.getOutputStream());

        File f = null;
        switch (request.getSpecialUrlType()) {
            case NOT_SPECIAL:
                try {
                    f = new File(this.rootDirectory + request.getRequest());

                    if (f == null) {
                        response.setStatusCode(HttpStatusCodeType._404);
                        request.setContentType(BasicFileType.HTML);
                        outputResponse(out, request, response, f);
                        break;
                    }

                    handleModified(f, request, response);
                    handleDirectory(f, request);

                    outputResponse(out, request, response, f);

                } catch (FileNotFoundException e) {
                    response.setStatusCode(HttpStatusCodeType._404);
                    request.setContentType(BasicFileType.HTML);
                    outputResponse(out, request, response, f);
                    // Dangerous, passing in a null fileIn object.
                }
                break;

            case CONTROL:
                outputControl(out, request, response);
                break;

            case SHUTDOWN:
                this.stopRunning();
                this.parent.terminate();
                break;
        }

        logger.debug("Finished request: " + request.getRequest());
        requestReader.close();
        out.close();
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
            // Error
            outputConnectionClose(out);
            if (response.getStatusCode() == HttpStatusCodeType._404) {
                outputContentTypeLine(out, request);
                out.println("");
                out.println(HtmlStrings._404);
            }
        }
    }

    private void outputResponseHeader(PrintStream out,
                                      HttpResponseContext response) {
        out.println(generateHttpLine(response));
        out.println(generateDateLine());

    }

    private void outputDirectoryHtml(PrintStream out,
                                     HttpRequestContext request,
                                     HttpResponseContext response,
                                     File f)
    {
        // TODO Handle content-length?
        // Does not seem to matter.
        out.println("");

        StringBuilder sb = new StringBuilder();
        sb.append("<html><body>");
        sb.append("<link rel='stylesheet' href='http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css'>");
        sb.append("<div class='container container-fluid'>");
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
                line += "500 Internal Server Error";

        }
        return line;
    }

    private String generateDateLine() {
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
                logger.debug("Ignoring Content-Type: " + request.toString());
                line += "text/plain";
                // Should probably error
        }
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

    // Hardcode
    private void outputControl(PrintStream out, HttpRequestContext request, HttpResponseContext response) {
        int bros = this.parent.getNumWorkers();
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 200 OK\n");
        sb.append(generateDateLine() + "\n");
        sb.append("Content-Type: text/html\n");
        sb.append("\n");
        sb.append("<html><body>");
        sb.append("<link rel='stylesheet' href='http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css'>");
        sb.append("<div class='container container-fluid'>");
        sb.append("<div>");
        sb.append("<h1>");
        sb.append("Brian Shi(brishi)'s web server.");
        sb.append("");
        sb.append("</h1>");
        sb.append("</div>");
        sb.append("<div>");
        sb.append("<h2>");
        sb.append("Threads:");
        sb.append("</h2>");
        sb.append("<div>");
        for (int i = 0; i < bros; i++) {
            sb.append("<h4>");
            sb.append("#");
            sb.append(i);
            sb.append(":");
            sb.append(this.parent.getStatus(i));
            sb.append("</h4>");
        }
        sb.append("</div>");
        sb.append("</div>");
        sb.append("<div>");
        sb.append("<button type='button'>");
        sb.append("<a href='/shutdown'>");
        sb.append("SHUTDOWN");
        sb.append("</a>");
        sb.append("</button>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</body></html>");

        out.print(sb.toString());

    }

}
