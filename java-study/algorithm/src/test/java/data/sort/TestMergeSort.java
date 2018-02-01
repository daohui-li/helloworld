package data.sort;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class TestMergeSort {

    @Test
    public void testMergeSort() {
        int[] data = {1, 5, 0, -1, 7, -2};
        MergeSort.sort(data);
        int[] ref = {-2, -1, 0, 1, 5, 7};
        assertArrayEquals(ref, data);

        int[] data2 = {-1, -9, 1, 8, 0};
        MergeSort.sort(data2);
        int[] ref2 = {-9, -1, 0, 1, 8};
        assertArrayEquals(ref2, data2);

        int[] data3 = {0};
        MergeSort.sort(data3);
        assertArrayEquals(new int[1], data3);
    }
}
