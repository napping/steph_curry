package edu.upenn.cis.cis455.webserver.thread;

import edu.upenn.cis.cis455.webserver.blockingqueue.BlockingQueue;

import java.net.Socket;

/**
 * @author brishi
 */
public class ThreadPool {

    private BlockingQueue blockingQueue;
    private Thread[] workerThreads;

    private int numWorkers;

    public ThreadPool(int numWorkers) {
        this.blockingQueue = new BlockingQueue<Socket>();
        this.workerThreads = new Thread[numWorkers];

        this.numWorkers = numWorkers;
        this.startWorkerThreads();
    }

    private void startWorkerThreads() {
        for (int i = 0; i < this.numWorkers; i++) {
            this.workerThreads[i] = new Thread(
                    new QueueWorker(this.blockingQueue));
            this.workerThreads[i].start();
        }

    }

    public void addRequestSocket(Socket clientSocket)
            throws InterruptedException {

        this.blockingQueue.enqueue(clientSocket);
    }
}
