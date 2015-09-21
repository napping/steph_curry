package edu.upenn.cis.cis455.webserver.thread;

import edu.upenn.cis.cis455.webserver.blockingqueue.BlockingQueue;
import edu.upenn.cis.cis455.webserver.context.ServerContext;

import java.net.Socket;

/**
 * @author brishi
 */
public class ThreadPool {

    private BlockingQueue blockingQueue;
    private Thread[] workers;
    private ServerContext serverContext;

    private int numWorkers;

    public ThreadPool(int numWorkers, ServerContext serverContext) {
        this.blockingQueue = new BlockingQueue<Socket>();
        this.workers = new Thread[numWorkers];
        this.serverContext = serverContext;

        this.numWorkers = numWorkers;
        this.startWorkerThreads();
    }

    private void startWorkerThreads() {
        for (int i = 0; i < this.numWorkers; i++) {
            this.workers[i] = new Thread(
                    new QueueWorker(this.blockingQueue, this));

            this.workers[i].start();
        }

    }

    public void addRequestSocket(Socket clientSocket)
            throws InterruptedException {

        this.blockingQueue.enqueue(clientSocket);
    }

    public String getRootDirectory() {
        return this.serverContext.getRootDirectory();
    }
}
