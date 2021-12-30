package bst.avl;

import bst.KeyNode;

import java.util.Objects;

public class BalancedKeyNode<T> extends KeyNode<T> {
    private int balanceFactor;

    public BalancedKeyNode(int key, T data) {
        super(key, data);
        this.balanceFactor = 0;
    }

    public int getBalanceFactor() {
        return balanceFactor;
    }

    public void updateBalanceFactor() {
        this.balanceFactor = getSubtreeHeight(this.getSx()) - getSubtreeHeight(this.getDx());
    }

    public int getSubtreeHeight(KeyNode<T> current) {
        if (current != null)
            return getSubtreeHeight(current, 1);
        else
            return 0;
    }

    private int getSubtreeHeight(KeyNode<T> current, int currentHeight) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BalancedKeyNode<?> that = (BalancedKeyNode<?>) o;
        return balanceFactor == that.balanceFactor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), balanceFactor);
    }
}
