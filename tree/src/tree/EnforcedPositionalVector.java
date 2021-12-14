package tree;

import java.util.*;

/**
 * This class provides a basic implementation to the {@link Tree} interface, using the positions vector method with
 * enforced structure;
 *
 * @param <T> the type of the data contained in the nodes
 */
public class EnforcedPositionalVector<T> implements Tree<T> {

    /**
     * The list actually representing the positions vector with enforced structure.
     */
    private final List<Node<T>> vector;

    /**
     * Constructs a positions vector containing the nodes with the specified data. Note that every null value
     * in the array will be skipped without notice.
     * @param nodeData the data to be contained in the vector nodes
     * @apiNote For a null-safer construction that throws an {@link InputMismatchException} if a null value is
     * contained in the array, see {@link EnforcedPositionalVector#of(Object[])}
     */
    public EnforcedPositionalVector(T[] nodeData) {
        List<Node<T>> nodeVector = new ArrayList<>(nodeData.length);
        for (var data : nodeData) {
            if (data != null)
                nodeVector.add(new Node<>(data));
        }
        this.vector = nodeVector;
    }

    /**
     * Returns an {@code EnforcedPositionalVector} containing the nodes with the specified data; a null value in
     * such specified data will not be simply skipped and will result in an {@link InputMismatchException} being thrown.
     * @param nodeData the data to be contained in the vector nodes
     * @param <T> the type of the data to be contained in the nodes
     * @return an {@link EnforcedPositionalVector} containing the nodes with the specified data
     * @throws InputMismatchException if the array contains a null value
     */
    public static <T> EnforcedPositionalVector<T> of(T[] nodeData) throws InputMismatchException {
        List<Node<T>> nodeVector = new ArrayList<>(nodeData.length);
        for (var data : nodeData) {
            if (data == null)
               throw new InputMismatchException("Array parameter of method of() should not contain null values.");
        }
        return new EnforcedPositionalVector<>(nodeData);
    }

    /**
     * Creates a node containing the specified information and inserts it in this tree as the last element of the
     * vector, i.e. the rightmost leaf-node in this tree.
     * @param data the information that will be contained in the node inserted
     */
    @Override
    public void insert(T data) {
        vector.add(new Node<>(data));
    }

    @Override
    public Node<T> delete(T data) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Removal is only possible with the deleteRightmostLeaf() " +
                "method in this implementation");
    }
    
    /**
     * Deletes the rightmost leaf-node from this tree.
     * @return the removed node
     * @apiNote This is the only removal supported by this particular implementation, since there cannot be
     * "holes" in an enforced positional vector
     */
    public Node<T> deleteRightmostLeaf() {
        return vector.remove(vector.size() - 1);
    }

    @Override
    public Optional<Node<T>> search(T data) {
        for (var node : vector) {
            if (node.getData().equals(data))
                return Optional.of(node);
        }
        return Optional.empty();
    }

    @Override
    public void print() {
        System.out.println(vector);
    }
}
