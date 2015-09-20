package edu.upenn.cis.cis455.webserver.thread;

import edu.upenn.cis.cis455.webserver.blockingqueue.BlockingQueue;
import edu.upenn.cis.cis455.webserver.context.HttpRequestContext;
import edu.upenn.cis.cis455.webserver.context.HttpResponseContext;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;

/**
 * @author brishi
 */
public class QueueWorker implements Runnable {
    final Logger logger = Logger.getLogger(QueueWorker.class);

    private BlockingQueue<Socket> queue;
    private boolean RUNNING;

    public QueueWorker(BlockingQueue queue) {
        this.queue = queue;
        this.RUNNING = true;
    }

    @Override
    public void run() {
        while (this.RUNNING) {
            try {
                Socket request = queue.dequeue();
                this.handleRequest(request);
                // request.close();

            } catch (InterruptedException e) {
                logger.debug("InterruptedException thrown by worker. " +
                        "Error: " + e.getMessage());

            } catch (IOException e) {
                logger.debug("IOException thrown by worker. Error: " +
                        e.getMessage());
            }
        }
    }

    private void handleRequest(Socket request) throws IOException {
        BufferedReader requestReader = new BufferedReader(
                new InputStreamReader(request.getInputStream()));

        HttpRequestContext requestContext = new HttpRequestContext();
        parseRequestIntoContext(requestReader, requestContext);

        logger.debug("Done with handling request.");
    }

    private void parseRequestIntoContext(
            BufferedReader reader, HttpRequestContext requestContext)
            throws IOException {

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

    }
}
