package edu.upenn.cis.cis455.webserver.thread;

import edu.upenn.cis.cis455.webserver.blockingqueue.BlockingQueue;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
                Socket request = queue.deqeue();
                this.handleRequest(request);
                // request.close();

            } catch (InterruptedException e) {
                logger.debug("InterruptedException thrown by worker. Error: " + e.getMessage());

            } catch (IOException e) {
                logger.debug("IOException thrown by worker. Error: " + e.getMessage());
            }
        }
    }

    private void handleRequest(Socket request) throws IOException {
        BufferedReader requestReader = new BufferedReader(new InputStreamReader(request.getInputStream()));

        String line;
        while ((line = requestReader.readLine()) != null) {
            System.out.println(line);
        }

        logger.debug("Done with handling request.");
    }
}
