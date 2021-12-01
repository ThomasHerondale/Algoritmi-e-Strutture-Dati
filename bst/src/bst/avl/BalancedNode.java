package bst.avl;

import bst.Node;

public class BalancedNode<T> extends Node<T> {
    private int balanceFactor;

    public BalancedNode(int key, T data) {
        super(key, data);
        this.balanceFactor = 0;
    }

    public int getBalanceFactor() {
        return balanceFactor;
    }

    public void updateBalanceFactor() {
        this.balanceFactor = getSubtreeHeight(this.getSx()) - getSubtreeHeight(this.getDx());
    }

    private int getSubtreeHeight(Node<T> current) {
        return getSubtreeHeight(current, 1);
    }

    private int getSubtreeHeight(Node<T> current, int currentHeight) {
        if (current.childrenCount() == 0)
            return currentHeight;
        int sxHeight = 0;
        if (current.getSx() != null)
            sxHeight = getSubtreeHeight(current.getSx(), currentHeight + 1);
        int dxHeight = 0;
        if (current.getDx() != null)
            dxHeight = getSubtreeHeight(current.getDx(), currentHeight + 1);
        return Math.max(sxHeight, dxHeight);
    }
}
