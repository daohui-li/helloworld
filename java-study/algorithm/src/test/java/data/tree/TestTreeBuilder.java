package data.tree;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TestTreeBuilder {
    TreeBuilder<Integer> builder = new TreeBuilder<>();
    @Test
    public void testBuildUsingMiddleAsRoot() {
        System.out.println(builder.buildUsingMiddleAsRoot(1, 2, 3, 0));
        System.out.println(builder.buildUsingMiddleAsRoot(1, 2, 3, 4, 0));
        System.out.println(builder.buildUsingMiddleAsRoot(1, 2, 3, 4, 5, 0));

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6,7, 8, 9, 0);
        TreeNode<Integer> tree = builder.buildUsingMiddleAsRoot(list.toArray(new Integer[list.size()]));
        LinkedList<Integer> returnValue = TreeTraversal.traversalsTreeToLinkedListUsingMiddlePoint(tree);
        assertArrayEquals(list.toArray(), returnValue.toArray());
    }

    @Test
    public void testBuildUseLinkedList() {
        LinkedList<Integer> list = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 0));
        TreeNode<Integer> tree = builder.buildUseLinkedList(new LinkedList<>(list));
        System.out.println(tree);

        // round trip
        LinkedList<Integer> roundTrip = TreeTraversal.traversalsTreeToLinkedList(tree);
        System.out.println(roundTrip);
        assertEquals(list, roundTrip);
    }
}
