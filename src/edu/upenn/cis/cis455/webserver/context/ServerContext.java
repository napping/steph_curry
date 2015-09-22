package edu.upenn.cis.cis455.webserver.context;

import java.net.InetAddress;

/**
 * @author brishi
 *
 * Following the Context Object Design pattern.
 * Encapsulates all information about server into a general, portable, and
 * extensible form.
 *
 * More information:
 *      http://www.cs.wustl.edu/~schmidt/PDF/Context-Object-Pattern.pdf
 */
public class ServerContext {
    private String rootPath;
    private int port;
    private InetAddress address;
    private int numWorkers = 5;

    // Defaults
    public ServerContext() {
        this.rootPath = "/home/cis455/www";
        this.port = 8080;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
        if (rootPath.charAt(rootPath.length() - 1) != '/') {
            this.rootPath += "/";
        }
    }

    public void setPort(int port) {
        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("Invalid port number provided " +
                    "to ServerContext.");
        }
        this.port = port;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public void setNumWorkers(int num) {
        if (num > 200) {
            throw new IllegalArgumentException("Too many workers provided " +
                    "to ServerContext.");
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
