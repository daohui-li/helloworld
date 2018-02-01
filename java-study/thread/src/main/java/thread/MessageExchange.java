package thread;

import java.util.ArrayList;
import java.util.List;

public class MessageExchange {
    private String message = null;
    boolean isConsumed = true;

    private List<MessageConsumer> consumers = new ArrayList<>();

    public synchronized void put(String message) {
        System.out.println("received a message: " + message);
        while (!isConsumed) {
            try {
                System.out.println("wait until the exchange message" + this.message + " is consumed...");
                wait();
            } catch (InterruptedException e) {
            }
        }

        this.message = message;

        isConsumed = false;
        notify();  // because only two sync may happen at one time, this is same as notifyAll()
        System.out.println("notifyAll() (in put())");

    }

    public synchronized boolean get() {
        while (isConsumed) {
            try {
                System.out.println("wait for message...");
                wait();
            } catch (InterruptedException e) {
            }
        }

        for (MessageConsumer consumer : consumers) {
            consumer.consume(message);
        }
        boolean isDone = message.equalsIgnoreCase("done");
        message = null;
        isConsumed = true;
        notify();
        System.out.println("notifyAll() (in get()");
        return isDone;
    }

    public void register(MessageConsumer consumer) {
        if (!consumers.contains(consumer)) {
            consumers.add(consumer);
        }
    }
    @FunctionalInterface
    public interface MessageConsumer {
        void consume(String msg);
    }
}
