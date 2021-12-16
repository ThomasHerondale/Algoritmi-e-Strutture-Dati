package tree.linked;

import tree.Node;

/**
 * A simple node from a generic binary tree that uses a linked representation. The two children of this kind of node
 * are called left and right child for simplicity.
 * Note that this class uses a null reference to a child, being it either left or right, to actually indicate
 * the absence of such left or right child.
 * @param <T> the type of the data contained in the node
 */
public class LinkedBinaryNode<T> extends Node<T> {
    /**
     * The left child of this node.
     */
    private LinkedBinaryNode<T> sx;
    /**
     * The right child of this node.
     */
    private LinkedBinaryNode<T> dx;

    /**
     * Constructs a node with no children containing the sepcified data.
     *
     * @param data the information to be stored in the node
     */
    public LinkedBinaryNode(T data) {
        super(data);
        this.sx = null;
        this.dx = null;
    }

    /**
     * Returns the left child of this node.
     * @return a reference to the left child of this node
     */
    public LinkedBinaryNode<T> getSx() {
        return sx;
    }

    /**
     * Changes the left child of this node to the specified one.
     * @param sx the node that will become the left child of this node
     */
    public void setSx(LinkedBinaryNode<T> sx) {
        this.sx = sx;
    }

    /**
     * Returns the right child of this node.
     * @return a reference to the right child of this node
     */
    public LinkedBinaryNode<T> getDx() {
        return dx;
    }

    /**
     * Changes the right child of this node to the specified one.
     * @param dx the node that will become the right child of this node
     */
    public void setDx(LinkedBinaryNode<T> dx) {
        this.dx = dx;
    }
}
