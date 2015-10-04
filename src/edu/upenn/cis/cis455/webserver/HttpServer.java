package edu.upenn.cis.cis455.webserver;

import edu.upenn.cis.cis455.webserver.exception.InvalidDateException;
import edu.upenn.cis.cis455.webserver.server.Server;
import edu.upenn.cis.cis455.webserver.context.ServerContext;

/**
 * @author: brishi
 *
 * Acts like a launcher for the Server class, which initiates my daemon thread,
 * thread pool, blocking queue, HTTP parsing, etc.
 *
 */
class HttpServer {

    public static void main(String args[]) {

        if (args.length < 2) {
            System.out.println("Brian Shi - brishi");
            return;
        }

        System.out.println("Starting HTTP server.");

        try {
            ServerContext context = new ServerContext();

            int port = Integer.parseInt(args[0]);
            if (port > 0 && port < 65535) {
                context.setPort(port);
            } else {
                System.out.println("Invalid port number provided.");
                return;
            }

            context.setRootPath(args[1]);

            context.setNumWorkers(32);

            Server server = new Server(context);
            Thread serverThread = new Thread(server);
            server.setThread(serverThread);
            serverThread.start();

        } catch (NumberFormatException e) {
            System.out.println("Invalid port number provided.");
            return;
        }
    }

}
