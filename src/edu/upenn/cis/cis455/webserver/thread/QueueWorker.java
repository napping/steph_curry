package edu.upenn.cis.cis455.webserver.thread;

import edu.upenn.cis.cis455.webserver.blockingqueue.BlockingQueue;

/**
 * @author brishi
 */
public class QueueWorker extends Thread {
    BlockingQueue queue;

    public QueueWorker(BlockingQueue queue) {
        this.queue = queue;
    }

    public void start() {
    }
}
