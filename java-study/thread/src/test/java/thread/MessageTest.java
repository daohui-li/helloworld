package thread;

import org.junit.Test;

import java.util.Random;

public class MessageTest {
    Random random = new Random();
    @Test
    public void testMessages() throws InterruptedException {
        String[] messages = {"One", "Two", "Three", "Four"};

        MessageExchange exchange = new MessageExchange();
        exchange.register(this::handleMessage);

        Thread thread1 = new Thread(() -> {
            for (String message : messages) {
                System.out.println("sending " + message + "...");
                exchange.put(message);
            }
        });
        Thread thread2 = new Thread(() -> {
            while(!exchange.get()){}
            System.out.println("Thread2 is completed");
        });

        thread1.start();
        thread2.start();

        thread1.join();

        System.out.println("completed");
    }

    void handleMessage(String message) {
        System.out.println(message + " received");
        int msec = random.nextInt(5000);
        try{
            Thread.sleep(msec);
        } catch (InterruptedException e) {
        }
        System.out.println(message + " processed");
    }
}
