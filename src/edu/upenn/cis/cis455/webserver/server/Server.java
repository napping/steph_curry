package edu.upenn.cis.cis455.webserver.server;

import edu.upenn.cis.cis455.webserver.context.ServerContext;
import edu.upenn.cis.cis455.webserver.thread.ThreadPool;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author brishi
 */
public class Server implements Runnable {
    final Logger logger = Logger.getLogger(Server.class);

    private ServerSocket serverSocket;
    private ThreadPool pool;
    private Thread thread;

    private boolean RUNNING;

    private ServerContext context;

    public Server(ServerContext context) {
        this.context = context;

        this.pool = new ThreadPool(context.getNumWorkers(), this);
    }

    public ServerContext getContext() {
        return this.context;
    }

    public ThreadPool getPool() {
        return this.pool;
    }

    public void setThread(Thread t) {
        this.thread = t;
    }
    public void shutDown() throws IOException {
        this.RUNNING = false;
    }

    @Override
    public void run() {
        logger.debug("Running server...");
        this.RUNNING = true;

        Socket clientSocket;
        try {
            this.serverSocket = new ServerSocket(this.context.getPort());
            this.context.setAddress(serverSocket.getInetAddress());

            while (this.RUNNING) {
                clientSocket = serverSocket.accept();

                this.pool.addRequestSocket(clientSocket);
            }

            serverSocket.close();

        } catch (IOException e) {
            logger.debug("IOException thrown by sockets. " +
                    "Error: " + e.getMessage());

        } catch (InterruptedException e) {
            logger.debug("InterruptedException thrown by thread pool. " +
                    "Error: " + e.getMessage());
        }
    }
}
