package edu.upenn.cis.cis455.webserver.thread;

import edu.upenn.cis.cis455.webserver.blockingqueue.BlockingQueue;
import edu.upenn.cis.cis455.webserver.context.ServerContext;
import edu.upenn.cis.cis455.webserver.server.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.Queue;

/**
 * @author brishi
 */
public class ThreadPool {

    private BlockingQueue blockingQueue;
    private QueueWorker[] workers;
    private Thread[] threads;

    private Server server;
    private ServerContext serverContext;

    private int numWorkers;

    public ThreadPool(int numWorkers, Server server) {
        this.blockingQueue = new BlockingQueue<Socket>();

        this.workers = new QueueWorker[numWorkers];
        this.threads = new Thread[numWorkers];

        this.server = server;
        this.serverContext = server.getContext();

        this.numWorkers = numWorkers;
        this.startWorkerThreads();
    }

    private void startWorkerThreads() {
        for (int i = 0; i < this.numWorkers; i++) {
            this.workers[i] = new QueueWorker(this.blockingQueue, this);
            this.threads[i] = new Thread(this.workers[i]);
            this.threads[i].start();
        }

    }

    public void addRequestSocket(Socket clientSocket)
            throws InterruptedException {

        this.blockingQueue.enqueue(clientSocket);
    }

    public String getRootDirectory() {
        return this.serverContext.getRootDirectory();
    }

    public void terminate() throws IOException {
        for (int i = 0; i < this.numWorkers; i++) {
            this.workers[i].stopRunning();
            this.threads[i].interrupt();
        }

        this.server.shutDown();
    }

    public int getNumWorkers() {
        return this.numWorkers;
    }

    // "...'waiting' or the URL it is currently handling..."
    public String getStatus(int workerNum) {
        QueueWorker worker =  this.workers[workerNum];
        if (worker.WAITING) {
            return "WAITING";
        }
        return "Processing request: " + worker.getRequestContext().getRequest();
    }
}
