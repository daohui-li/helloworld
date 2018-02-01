package data;

import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class AtomicPropTest {
    @Test
    public void testAtomicProp() {
        AtomicProp prop = new AtomicProp();
        Runnable runnable = () -> prop.inc();

        runTask(runnable, 1, 100);
        System.out.println("1 thread delays 100msec");
        System.out.println("total: " + prop.get());
        System.out.println("delta: " + prop.check());
        assertTrue(prop.check()==0);  // regular integer value is same as data's

        prop.reset();

        runTask(runnable, 5, 100);
        System.out.println("5 thread delays 100msec");
        System.out.println("total: " + prop.get());
        System.out.println("delta: " + prop.check());
        assertTrue(prop.check()!=0);
    }

    private void runTask(Runnable r, int howManyThread, int waitMsec) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(howManyThread);
        ScheduledFuture[] futures = new ScheduledFuture[howManyThread];
        for (int i=0; i<howManyThread; i++) {
            futures[i] = service.scheduleAtFixedRate(r, 0, 1, TimeUnit.MICROSECONDS);
        }
        try {
            Thread.sleep(waitMsec);
        } catch (InterruptedException e) {
        }

        for (int i=0; i<howManyThread; i++) {
            assertTrue(!futures[i].isCancelled());
            futures[i].cancel(true);
            assertTrue(futures[i].isCancelled());
        }
    }
}
