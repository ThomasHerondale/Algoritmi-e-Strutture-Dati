package tree;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * An interface representing a simple tree data structure. In the most general case, it supports three basic
 * operations: insert, search, delete.
 * Note that some of these operations may not be supported by every implementation, and may result in
 * an {@link UnsupportedOperationException} being thrown.
 * @param <T> the type of the data contained in the nodes
 */
public interface Tree<T> {
    /**
     * Creates a node containing the specified information and inserts it in this tree.
     * @param data the information that will be contained in the node inserted
     * @throws UnsupportedOperationException if this operation is not supported for the particular implementation
     */
    void insert(T data) throws UnsupportedOperationException;

    /**
     * Deletes the node containing the specified information from this tree.
     * @param data the information contained in the node to remove
     * @return the removed node
     * @throws UnsupportedOperationException if this operation is not supported for the particular implementation
     * @throws NoSuchElementException if no node containing the specified information is present
     */
    Node<T> delete(T data) throws UnsupportedOperationException, NoSuchElementException;

    /**
     * Searches for the node containing the specified information in this tree.
     * @param data the information contained in the node to search
     * @return an empty {@link Optional} if no node matching the specified data is found, an {@link Optional}
     * containing such node otherwise
     */
    Optional<Node<T>> search(T data);

    /**
     * Prints this tree to the standard output. Note that the way the tree gets printed is strongly
     * implementation-dependent and may vary consistently from one implementation to another.
     */
    void print();
}
