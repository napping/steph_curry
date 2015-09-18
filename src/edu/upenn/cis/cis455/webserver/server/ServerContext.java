package edu.upenn.cis.cis455.webserver.server;

import edu.upenn.cis.cis455.webserver.thread.ThreadPool;

import java.net.InetAddress;
import java.util.Map;

/**
 * @author brishi
 */
public class ServerContext {
    private String rootPath;
    private int port;
    private InetAddress address;
    private int numWorkers = 50;

    // Defaults
    public ServerContext() {
        this.rootPath = "/home/cis455/www";
        this.port = 8080;
    }

    public ServerContext(Map<String, Object> args) {
        this.port = (int) args.get("port");
        this.rootPath = (String) args.get("rootPath");
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public void setPort(int port) {
        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("Invalid port number provided to ServerContext.");
        }
        this.port = port;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public void setNumWorkers(int num) {
        if (num > 200) {
            throw new IllegalArgumentException("Too many workers.");
        }
        this.numWorkers = num;
    }

    public String getRootDirectory() {
        return this.rootPath;
    }

    public int getPort() {
        return this.port;
    }

    public InetAddress getAddress() {
        return this.address;
    }

    public int getNumWorkers() {
        return this.numWorkers;

    }
}
