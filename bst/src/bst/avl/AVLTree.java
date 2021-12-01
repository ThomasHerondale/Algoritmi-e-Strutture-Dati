package bst.avl;

import bst.BinarySearchTree;
import bst.Node;

public class AVLTree<T> extends BinarySearchTree<T> {

    public AVLTree(int key, T data) {
        super(new BalancedNode<>(key, data));
    }

    @Override
    public void insert(Node<T> newNode) {
        super.insert(newNode);
    }

    @Override
    public Node<T> delete(int key) {
        return super.delete(key);
    }
}
