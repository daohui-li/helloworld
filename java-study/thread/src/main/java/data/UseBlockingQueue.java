package data;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class UseBlockingQueue {
    // Note: BlockingQueue is thread safe, but test shows the reqular queue seems also thread safe (see test)
    private BlockingDeque<Integer> queue = new LinkedBlockingDeque<>();
    private Queue<Integer> regularQueue = new LinkedList<>();

    public void push(int value) {
        queue.add(value);
        regularQueue.add(value);
    }

    public int pop() {
        return queue.pop();
    }

    public int regularPop() {
        return regularQueue.remove();
    }
}
