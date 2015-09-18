package edu.upenn.cis.cis455.webserver.thread;

import edu.upenn.cis.cis455.webserver.blockingqueue.BlockingQueue;

import java.net.Socket;

/**
 * @author brishi
 */
public class ThreadPool {

    private BlockingQueue blockingQueue;
    private Thread[] workers;

    private int numWorkers;

    public ThreadPool(int numWorkers) {
        this.blockingQueue = new BlockingQueue();
        this.workers = new QueueWorker[numWorkers];

        this.numWorkers = numWorkers;
        this.startWorkerThreads();
    }

    private synchronized void startWorkerThreads() {

        for (int i = 0; i < this.numWorkers; i++) {
            this.workers[i] = new QueueWorker(this.blockingQueue);
            this.workers[i].start();
        }

    }

    public void addRequestSocket(Socket clientSocket) throws InterruptedException {
        this.blockingQueue.enqueue(clientSocket);
    }
}
