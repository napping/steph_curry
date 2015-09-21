package edu.upenn.cis.cis455.webserver;

import edu.upenn.cis.cis455.webserver.server.Server;
import edu.upenn.cis.cis455.webserver.context.ServerContext;

/**
 * @author: brishi
 *
 * Acts like a launcher for the Server class, which initiates my thread pool,
 * blocking queue, HTTP parsing, etc.
 *
 */
class HttpServer {

    public static void main(String args[]) {
        System.out.println("Starting HTTP server.");

        if (args.length < 2) {
            System.out.println("Brian Shi - brishi");
        }

        try {
            ServerContext context = new ServerContext();
            context.setPort(Integer.parseInt(args[0]));
            context.setRootPath(args[1]);
            // context.setNumWorkers(32);  Defaults to 5

            Thread serverThread = new Thread(new Server(context));
            serverThread.start();

        } catch (Exception e) {
            // TODO Handle
        }
    }

}
