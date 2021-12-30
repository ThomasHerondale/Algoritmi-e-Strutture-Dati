package tree.linked;

import tree.Node;

import java.util.ArrayList;
import java.util.List;

public class LinkedNode<T> extends Node<T> {
    /**
     * The list containing all the children of this node.
     */
    private final List<LinkedNode<T>> children;

    /**
     * Constructs a node with the specified data.
     *
     * @param data the information to be stored in the node
     */
    public LinkedNode(T data) {
        super(data);
        this.children = new ArrayList<>();
    }

    /**
     * Returns a list containing all the child nodes of this node.
     * @return a list containing the children of this node
     */
    public List<LinkedNode<T>> getChildren() {
        return children;
    }

    /**
     * Returns the number of the child nodes of this node.
     * @return the size of the {@code children} list of this node
     */
    public int childrenNumber() {
        return children.size();
    }

    public void addChildren(LinkedNode<T> node) {
        children.add(node);
    }
}
