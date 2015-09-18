package edu.upenn.cis.cis455.webserver.server;

import edu.upenn.cis.cis455.webserver.thread.ThreadPool;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author brishi
 */
public class Server extends Thread {
    final Logger logger = Logger.getLogger(Server.class);
    private boolean RUNNING;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ThreadPool pool;

    ServerContext context;

    public Server(ServerContext context) {
        this.context = context;

        this.pool = context.getThreadPool();
    }

    @Override
    public void run() {
        this.RUNNING = true;
        try {
            serverSocket = new ServerSocket(this.context.getPort());
            context.setAddress(serverSocket.getInetAddress());

            while (RUNNING) {
                clientSocket = serverSocket.accept();

                this.pool.addRequestSocket(clientSocket);
            }

        } catch (IOException e) {
            logger.debug("IOException thrown by sockets. Error: " + e.getMessage());
        } catch (InterruptedException e) {
            logger.debug("InterruptedException thrown by thread pool. Error: " + e.getMessage());
        }
    }
}
