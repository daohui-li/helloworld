package forknjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FibonacciTask extends RecursiveTask<Integer> {
    public static int compute(int n) {
        ForkJoinPool pool = new ForkJoinPool();
        return pool.invoke(new FibonacciTask(n));
    }

    final int n;

    public FibonacciTask(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n <= 1) {
            return n;
        }

        FibonacciTask subTask1 = new FibonacciTask(n-1);
        subTask1.fork();
        FibonacciTask subTask2 = new FibonacciTask((n-2));
        return subTask2.compute() + subTask1.join();  // NOTE: subTask1 is first fork()ed and then join()ed
    }
}
