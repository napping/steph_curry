package edu.upenn.cis.cis455.webserver.server;

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
    private Socket clientSocket;
    private ThreadPool pool;

    private boolean RUNNING;

    private ServerContext context;

    public Server(ServerContext context) {
        this.context = context;

        logger.debug("Server instantiated.");
        this.pool = new ThreadPool(context.getNumWorkers());
    }

    @Override
    public void run() {
        logger.debug("Running server");
        System.out.println("_______________");
        this.RUNNING = true;
        try {
            serverSocket = new ServerSocket(this.context.getPort());
            context.setAddress(serverSocket.getInetAddress());

            while (RUNNING) {
                clientSocket = serverSocket.accept();

                this.pool.addRequestSocket(clientSocket);
            }

            serverSocket.close();

        } catch (IOException e) {
            logger.debug("IOException thrown by sockets. Error: " + e.getMessage());

        } catch (InterruptedException e) {
            logger.debug("InterruptedException thrown by thread pool. Error: " + e.getMessage());
        }
    }
}
