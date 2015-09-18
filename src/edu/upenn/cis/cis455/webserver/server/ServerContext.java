package edu.upenn.cis.cis455.webserver.server;

import edu.upenn.cis.cis455.webserver.thread.ThreadPool;

import java.net.InetAddress;

/**
 * @author brishi
 */
public class ServerContext {
    private String rootPath;
    private int port;
    private ThreadPool threadPool;
    private InetAddress address;

    // Defaults
    public ServerContext() {
        this.rootPath = "/home/cis455/www";
        this.port = 8080;
        this.threadPool = new ThreadPool(100); // TODO: 100?
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

    public void setThreadPool(ThreadPool threadPool) {
        this.threadPool = threadPool;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public String getRootDirectory() {
        return this.rootPath;
    }

    public int getPort() {
        return this.port;
    }

    public ThreadPool getThreadPool() {
        return this.threadPool;
    }

    public InetAddress getAddress() {
        return this.address;
    }
}
