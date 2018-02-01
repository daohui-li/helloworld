package forknjoin;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class FibonacciTaskTest {
    @Test
    public void testCompute() {
        int pre2 = FibonacciTask.compute(0);
        assertEquals(0, pre2);
        int pre1 = FibonacciTask.compute(1);
        assertEquals(1, pre1);
        for (int i=2; i<10; i++) {
            int curr = FibonacciTask.compute(i);
            assertEquals(pre1+pre2, curr);
            System.out.println("i=" + i + " ==> " + curr);
            pre2 = pre1;
            pre1 = curr;
        }
    }
}
