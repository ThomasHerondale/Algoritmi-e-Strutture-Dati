package tree;

import java.util.*;
import java.util.function.Consumer;

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
     * The maximum number of children of every node of this m-ary tree.
     */
    private int m;

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
        this.m = -1;
    }

    /**
     * Constructs a positions vector, with each node having at most the specified number of children and containing
     * the specified data. Note that every null value in the array will be skipped without notice.
     * @param nodeData the data to be contained in the vector node
     * @param maxChildren the maximum number of children of every node in this tree
     * @implSpec It's important to note that some methods of this class may not behave as expected or may result
     * in an {@link UnsupportedOperationException} if the tree was not meant to be
     * an m-ary complete tree.
     * This constructor should only be used in the case of such trees.
     * @apiNote Note that the {@code maxChildren} parameter represents the m in the 'm-ary tree' naming
     */
    public EnforcedPositionalVector(T[] nodeData, int maxChildren) {
        this(nodeData);
        this.m = maxChildren;
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

    /**
     * Scans systematically every node in this tree, hence performing the given action on every node in this tree,
     * only if this tree is an m-ary complete tree and resulting in an {@link UnsupportedOperationException} otherwise.
     * This search is intended for reaching the deppest leaf in this tree before everything else, and trying to do
     * the same for every given subtree.
     * @param action the action to be performed on every node at scan time
     * @throws UnsupportedOperationException if this is not an m-ary tree
     */
    public void depthFirstSearch(Consumer<Node<T>> action) throws UnsupportedOperationException {
        if (m == -1)
            throw new UnsupportedOperationException("Depth first search is not allowed for non m-ary trees.");
        else
            depthFirstSearch(action, m, 0);
    }

    private void depthFirstSearch(Consumer<Node<T>> action, int m, int pointer) {
        if (pointer >= vector.size())
            return;
        action.accept(vector.get(pointer));
        for (var i = 1; i <= m; i++) {
            depthFirstSearch(action, m, pointer * m + i);
        }
    }

    @Override
    public void print() {
        System.out.println(vector);
    }
}
