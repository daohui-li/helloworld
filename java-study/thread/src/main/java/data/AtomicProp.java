package data;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicProp {
    int regularCounter = 0;
    AtomicInteger atomicInteger = new AtomicInteger(0);

    public void inc() {
        regularCounter++;
        atomicInteger.addAndGet(1);
    }

    public int check() {
        return atomicInteger.get() - regularCounter;
    }

    public int get() {
        return atomicInteger.get();
    }

    public void reset() {
        regularCounter = 0;
        atomicInteger.set(0);
    }
}
