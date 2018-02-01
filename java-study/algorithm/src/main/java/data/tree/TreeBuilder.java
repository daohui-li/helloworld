package data.tree;

import java.util.LinkedList;
import java.util.Queue;

public class TreeBuilder<V> {
    public TreeNode<V> buildUsingMiddleAsRoot(V... array) {
        if (array == null || array.length == 0) {
            return null;
        }

        return buildUsingMiddleAsRoot(array, 0, array.length);
    }

    public TreeNode<V> buildUseLinkedList(LinkedList<V> list) {
        if (list == null || list.size() == 0) {
            return null;
        }

        // init
        TreeNode<V> top = new TreeNode<V>(list.remove());
        Queue<TreeNode<V>> queue = new LinkedList<>();
        queue.add(top);

        // loop
        while (list.size() > 0) {
            TreeNode<V> current = queue.remove();
            TreeNode<V> node = new TreeNode<V>(list.remove());
            current.setLeft(node);
            queue.add(node);
            if (list.size() > 0) {
                node = new TreeNode<V>(list.remove());
                current.setRight(node);
                queue.add(node);
            }
        }
        return top;
    }

    private TreeNode<V> buildUsingMiddleAsRoot(V[] array, int min, int max) {  // [min, max)
        // boundary
        if (min >= max) {
            return null;
        }

        // increase performance a bit by using following code?
//        if (min+1 == max) {
//            return new TreeNode<V>(array[min]);
//        }

        int mid = (min + max) / 2;
        TreeNode<V> left = buildUsingMiddleAsRoot(array, min, mid);
        TreeNode<V> right = buildUsingMiddleAsRoot(array, mid + 1, max);
        return new TreeNode<V>(array[mid], left, right);
    }
}
