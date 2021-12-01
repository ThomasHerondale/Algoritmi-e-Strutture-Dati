package bst.avl;

import bst.Node;

public class BalancedNode<T> extends Node<T> {
    private int balanceFactor;

    public BalancedNode(int key, T data) {
        super(key, data);
        this.balanceFactor = 0;
    }
}
