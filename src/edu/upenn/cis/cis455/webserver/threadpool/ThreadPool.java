package edu.upenn.cis.cis455.webserver.threadpool;

import edu.upenn.cis.cis455.webserver.blockingqueue.BlockingQueue;

/**
 * @author brishi
 */
public class ThreadPool {
    private BlockingQueue blockingQueue;
    private int size;

    public ThreadPool(int size) {
        this.blockingQueue = new BlockingQueue();
        this.size = size;

    }

}
