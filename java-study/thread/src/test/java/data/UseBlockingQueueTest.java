package data;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class UseBlockingQueueTest {

    private int maxNumber = 30;
    private int current = 0;

    ScheduledExecutorService service;
    UseBlockingQueue blockingQueue = new UseBlockingQueue();
    BlockingDeque<ScheduledFuture<?>> queue = new LinkedBlockingDeque<>();
    List<Integer> regularList = new ArrayList<>();
    List<Integer> blockedList = new ArrayList<>();

    boolean isDone = false;

    @Test
    public void testQueue() {
        service = Executors.newScheduledThreadPool(20);  // must be larger than 2
        ScheduledFuture<?> sendFuture = service.schedule(() -> scheduleSendTasks(), 0, TimeUnit.MICROSECONDS);
        ScheduledFuture<?> recvFuture = service.schedule(() -> checkTasks(), 100, TimeUnit.MILLISECONDS);
        while (!recvFuture.isDone()) {
            Thread.yield();
        }
        System.out.println("recvFuture is done");
        assertTrue(recvFuture.isDone());
        assertTrue(sendFuture.isDone());
        String regular = regularList.stream()
                .map(i -> {
                    return i.toString();
                })
                .collect(Collectors.joining(", "));
        System.out.println("reqular: " + regular);
        String blocked = blockedList.stream()
                .map(i -> {
                    return i.toString();
                })
                .collect(Collectors.joining(", "));
        System.out.println("blocked: " + blocked);
        assertEquals(blocked, regular);
    }

    private void scheduleSendTasks() {
        System.out.println("enter scheduleSendTasks()...");
        for (int current = 0; current < maxNumber; current++) {
            final int v = current;
            final ScheduledFuture<?> future = service.schedule(() -> blockingQueue.push(v), 0, TimeUnit.NANOSECONDS);
            queue.add(future);
            System.out.println("send out " + v);
        }
        isDone = true;
    }

    private boolean waitForData() {
        synchronized (queue) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
            System.out.println("queue size(from recv prespective): " + queue.size());
            return queue.isEmpty();
        }
    }

    private void checkTasks() {
        while ( !queue.isEmpty() || !isDone) {
            if (queue.isEmpty()) {
                Thread.yield();
                continue;  // use wait()?
            }
            ScheduledFuture<?> oldest;
            oldest = queue.remove();
            while (!oldest.isDone()) {
                Thread.yield();
            }
            int i = blockingQueue.pop();
            int j = blockingQueue.regularPop();
            blockedList.add(i);
            regularList.add(j);
            System.out.println("blocked: " + i + "; regular " + j);
        }
    }

}
