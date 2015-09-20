package edu.upenn.cis.cis455.webserver.blockingqueue;

import java.util.LinkedList;
import java.util.List;

/**
 * @author brishi
 */
public class BlockingQueue<K> {
    private final List<K> queue;
    int maxSize = 64;

    public BlockingQueue() {
        queue = new LinkedList<>();
    }

    public BlockingQueue(int maxSize) {
        queue = new LinkedList<>();
        this.maxSize = maxSize;
    }

    public synchronized void enqueue(K elt) throws InterruptedException {
        while (queue.size() >= this.maxSize) {
            System.out.print("Q is full! ");
            this.wait();
        }

        boolean notify = false;
        if (this.isEmpty()) {
            notify = true;
        }

        queue.add(elt);

        if (notify) {
            this.notifyAll();
        }
    }

    public synchronized K deqeue() throws InterruptedException {
        while (queue.isEmpty()) {
            System.out.print("Q is empty. ");
            this.wait();
        }

        boolean notify = false;
        if (this.isFull()) {
            notify = true;
        }

        K head = queue.remove(0);

        if (notify) {
            this.notifyAll();
        }

        return head;
    }

    public synchronized boolean isEmpty() {
        return queue.size() == 0;
    }

    public synchronized boolean isFull() {
        return queue.size() == this.maxSize;
    }

    public synchronized int getSize() {
        return queue.size();
    }
}
