package data.tree;

import java.util.LinkedList;
import java.util.Queue;

public class TreeTraversal {
    public static <V> LinkedList<V> traversalsTreeToLinkedList(TreeNode<V> tree) {
        if (tree == null) {
            return null;
        }

        // init
        LinkedList<V> list = new LinkedList<>();
        Queue<TreeNode<V>> queue = new LinkedList<>();
        queue.add(tree);

        // loop
        while (queue.size() > 0) {
            TreeNode<V> node = queue.remove();
            list.add(node.value);
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }

        return list;
    }

    public static <V> LinkedList<V> traversalsTreeToLinkedListUsingMiddlePoint(TreeNode<V> tree) {
        if (tree == null) {
            return null;
        }

        LinkedList<V> left = traversalsTreeToLinkedListUsingMiddlePoint(tree.left);
        LinkedList<V> right= traversalsTreeToLinkedListUsingMiddlePoint(tree.right);

        if (left == null) {
            left = new LinkedList<V>();
        }
        left.add(tree.value);
        if (right != null) {
            left.addAll(right);
        }

        return left;
    }
}
