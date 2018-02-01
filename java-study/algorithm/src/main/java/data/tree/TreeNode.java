package data.tree;

import java.lang.reflect.Array;

public class TreeNode<V> {
    V value;
    TreeNode<V> left;
    TreeNode<V> right;

    public TreeNode(V v) {
        this(v, null, null);
    }

    public TreeNode(V v, TreeNode<V> left, TreeNode<V> right) {
        this.value = v;
        this.left = left;
        this.right = right;
    }

    public void setLeft(TreeNode<V> node) {
        this.left = node; // verify left has been set already?
    }

    public void setRight(TreeNode<V> node) {
        this.right = node;
    }

    @Override
    public String toString() {
        String sLeft = (this.left != null) ? "[ " + this.left.toString() + " ]" : "";
        String sRight = (this.right != null) ? "[ " + this.right.toString() + " ]" : "";
        return sLeft + this.value + sRight;
    }
}
