package forknjoin;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class SortAction extends RecursiveAction {

    public static void mergeSort(int[] array) {
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new SortAction(array, 0, array.length));
    }

    private static int THRESHOLD = 1;
    private int[] array;
    private int min;
    private int max;

    public SortAction(int[] array, int min, int max) {
        this.array = array;
        this.min = min;
        this.max = max;
    }

    @Override
    protected void compute() {
        if (max - min <= THRESHOLD) {
            computeDirectly();
            return;
        }
        int mid = (max + min) >>> 1;  // same as (max+min)/2
        invokeAll(new SortAction(array, min, mid), new SortAction(array, mid, max));
        merge(mid);
    }

    private void merge(int mid) {
        int[] cache = Arrays.copyOfRange(array, min, mid);  // cache the left part as the values may be overwritten
        for (int left = 0, right = mid, c = min; left < cache.length; c++) {
            array[c] = (right >= max || cache[left] < array[right])  // right index reaches the limit, or buff's smaller than right one
                    ? cache[left++] : array[right++];
        }
    }

    private void computeDirectly() {
        // THRESHOLD = 1; do nothing
    }
}
