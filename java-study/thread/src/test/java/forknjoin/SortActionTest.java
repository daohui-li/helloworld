package forknjoin;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class SortActionTest {
    @Test
    public void testAlreadySorted() {
        int[] array = {1, 2, 6, 8, 10};
        int[] expected = {1, 2, 6, 8, 10};
        SortAction.mergeSort(array);
        assertArrayEquals(expected, array);
    }

    @Test
    public void testAlreadySortedInv() {
        int[] array = {10, 8, 6, 2, 1};
        int[] expected = {1, 2, 6, 8, 10};
        SortAction.mergeSort(array);
        assertArrayEquals(expected, array);
    }

    @Test
    public void testAlreadySortedInv2() {
        int[] array = {10, 8, 6, 2, 1, 1};
        int[] expected = {1, 1, 2, 6, 8, 10};
        SortAction.mergeSort(array);
        assertArrayEquals(expected, array);
    }

    @Test
    public void testSort1() {
        int[] array = {10, 6, 8, 2, 1, 3};
        int[] expected = {1, 2, 3, 6, 8, 10};
        SortAction.mergeSort(array);
        assertArrayEquals(expected, array);
    }

    @Test
    public void testSort2() {
        int[] array = {2, 10, 6, 1, 8, 3};
        int[] expected = {1, 2, 3, 6, 8, 10};
        SortAction.mergeSort(array);
        assertArrayEquals(expected, array);
    }
}
