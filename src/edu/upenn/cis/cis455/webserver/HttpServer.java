package edu.upenn.cis.cis455.webserver;

import edu.upenn.cis.cis455.webserver.server.Server;
import edu.upenn.cis.cis455.webserver.server.ServerContext;

class HttpServer {

    public HttpServer() {
        ServerContext context = new ServerContext();
        Server server = new Server(context);
        server.run();
    }

    public static void main(String args[]) {
          /* DAEMON THREAD GOES HERE */
        /* your code here */
    }

}
