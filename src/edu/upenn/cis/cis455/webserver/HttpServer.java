package edu.upenn.cis.cis455.webserver;

import edu.upenn.cis.cis455.webserver.server.Server;
import edu.upenn.cis.cis455.webserver.server.ServerContext;

class HttpServer {

    public static void main(String args[]) {
        if (args.length < 2) {
            System.out.println("Brian Shi - brishi");
        }

        try {
            ServerContext context = new ServerContext();
            context.setPort(Integer.parseInt(args[0]));
            context.setRootPath(args[1]);
            context.setNumWorkers(32);

            Server server = new Server(context);
            server.run();
        } catch (Exception e) {
            // TODO Handle
        }
    }

}
