package tree;

/**
 * A simple node of a tree or a graph, containing only some data of a generic type.
 * @param <T> the type of the data contained in the node
 */
public class Node<T> {
    /**
     * The data stored in the node.
     */
    private T data;

    /**
     * Constructs a node with the specified data.
     * @param data the information to be stored in the node
     */
    public Node(T data) {
        this.data = data;
    }

    /**
     * Returns the information stored in this node.
     * @return the information stored in this node.
     */
    public T getData() {
        return data;
    }

    /**
     * Changes the data contained in this node to the specified one.
     * @param data the data that will now be contained in this node.
     */
    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
