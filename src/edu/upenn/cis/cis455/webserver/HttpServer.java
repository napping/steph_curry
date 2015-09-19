package edu.upenn.cis.cis455.webserver;

import edu.upenn.cis.cis455.webserver.server.Server;
import edu.upenn.cis.cis455.webserver.server.ServerContext;
import org.apache.log4j.Logger;

class HttpServer {

    public static void main(String args[]) {
        System.out.println("Starting HTTP server. jajajajajaja");

        if (args.length < 2) {
            System.out.println("Brian Shi - brishi");
        }

        try {
            ServerContext context = new ServerContext();
            context.setPort(Integer.parseInt(args[0]));
            context.setRootPath(args[1]);
            context.setNumWorkers(32);

            Server server = new Server(context);
            server.start();

        } catch (Exception e) {
            // TODO Handle
        }
    }

}
