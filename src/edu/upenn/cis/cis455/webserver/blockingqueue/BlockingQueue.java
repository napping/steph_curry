package edu.upenn.cis.cis455.webserver.blockingqueue;

import java.util.LinkedList;
import java.util.List;

/**
 * @author brishi
 */
public class BlockingQueue {
    private final List queue;
    int maxSize = 64;

    public BlockingQueue() {
        queue = new LinkedList<>();
    }

    public BlockingQueue(int maxSize) {
        queue = new LinkedList<>();
        this.maxSize = maxSize;
    }

    public synchronized void enqueue(Object elt) throws InterruptedException {
        while (queue.size() >= this.maxSize) {
            synchronized (queue) {
                System.out.println("Queue is now full!");
                queue.wait();
            }
        }

        synchronized (queue) {
            queue.add(elt);
            queue.notifyAll();
        }
    }

    public synchronized Object deqeue() throws InterruptedException {
        while (queue.isEmpty()) {
            synchronized (queue) {
                System.out.println("Queue is now empty.");
                queue.wait();
            }
        }

        synchronized (queue) {
            queue.notifyAll();
            return queue.remove(0);
        }
    }

    public synchronized boolean isEmpty() {
        return queue.size() == 0;
    }

    public synchronized int getSize() {
        return queue.size();
    }
}
