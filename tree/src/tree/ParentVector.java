package tree;

import java.util.*;
import java.util.function.Consumer;

/**
 * This class provides a basic implementation to the {@link Tree} interface, using the parents vector method.
 * This particular implementation is only useful to the making of read-only m-ary trees, as the insertion and removal of
 * any node will result in throwing an {@link java.lang.UnsupportedOperationException}.
 * @param <T> the type of the data contained in the nodes
 */
public class ParentVector<T> implements Tree<T> {
    /**
     * The list actually representing the parents vector. This list is passed to {@link Collections#unmodifiableList(List)}
     * at creation time, hence it is not editable in any way; an attempt to modify such list may result in
     * an {@link UnsupportedOperationException} due to a call to the {@link ParentVector#insert} and
     * {@link ParentVector#delete} methods of this class, or in an {@link UnsupportedOperationException} being thrown
     * as per the {@link Collections#unmodifiableList(List)} specification.
     */
    private List<ParentVectorEntry> vector;
    /**
     * Constructs a parents vector matching every node with the index of its parent, following the same
     * order for the two lists.
     * @param nodeList the list containing all the nodes of the vector
     * @param parentlist the list containing the indexes of every parent of the nodes
     * @throws IllegalArgumentException if the two lists differ in size
     * @implSpec Note that the root of the tree should be located at the first index of the list. This
     * is not mandatory, but strongly recommended. The {@code Integer} index associated with such root must be
     * set to null
     */
    public ParentVector(List<Node<T>> nodeList, List<Integer> parentlist) throws IllegalArgumentException {
        if (nodeList.size() != parentlist.size())
            throw new IllegalArgumentException("Number of nodes is not equal to number of parents");
        vector = new ArrayList<>(nodeList.size());
        for (var i = 0; i < nodeList.size(); i++) {
            var parentIndex = parentlist.get(i) != null ? parentlist.get(i) : null;
            vector.add(new ParentVectorEntry(nodeList.get(i), parentIndex));
        }
        vector = Collections.unmodifiableList(vector);
    }

    @Override
    public void insert(T data) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Operation is not supported for the current implementation.");
    }

    @Override
    public Node<T> delete(T data) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Operation is not supported for the current implementation.");
    }

    @Override
    public Optional<Node<T>> search(T data) {
        try {
            return Optional.of(vector.get(find(data)).node);
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    @Override
    public void print() {
        for (var entry : vector) {
            System.out.print("[" + entry.node + ", ");
            if (entry.parentIndex != null)
                System.out.print(entry.parentIndex);
            else
                System.out.print("null");
            System.out.print("]\n");
        }
    }

    /**
     * Returns a list containing all the children of the node designated by the specified information.
     * @param parentData the information contained in the node
     * @return a {@link List} containing all the children of the specified node
     * @throws NoSuchElementException if no node containing the specified information is present
     */
    public List<Node<T>> findChildren(T parentData) throws NoSuchElementException {
        var children = new ArrayList<Node<T>>();
        var parentIndex = find(parentData);
        for (var entry : vector) {
            if (entry.parentIndex == null)
                continue;
            if (entry.parentIndex == parentIndex)
                children.add(entry.node);
        }
        return children;
    }

    /**
     * Searches for the parent of the node containing the specified information.
     * @param childData the information contained in the node
     * @return an {@link Optional} containing the parent of the specified node, or an empty
     * {@link Optional} if the specified information was contained in the root
     * @throws NoSuchElementException if no node containing the specified information is present
     */
    public Optional<Node<T>> findParent(T childData) throws NoSuchElementException {
        var index = find(childData);
        ParentVectorEntry child = vector.get(index);
        if (child.parentIndex != null) {
            var parent = vector.get(child.parentIndex).node;
            return Optional.of(parent);
        }
        else
            return Optional.empty();
    }

    /**
     * Scans systematically every node in this tree, hence performing the given action on every node in this tree.
     * This search is intended for reaching the deppest leaf in this tree before everything else, and trying to do
     * the same for every given subtree.
     * @param action the action to be performed on every node at scan time
     */
    public void depthFirstSearch(Consumer<Node<T>> action) {
        // TODO: attualmente basato sui figli della radice: sensato SOLO SE l'albero è completo, modificare
        int childNumber = findChildren(vector.get(0).node.getData()).size();
        depthFirstSearch(action, childNumber, 0);
    }

    private void depthFirstSearch(Consumer<Node<T>> action, int m, int pointer) {
        if (pointer >= vector.size())
            return;
        action.accept(vector.get(pointer).node);
        for (var i = 1; i <= m; i++) {
            depthFirstSearch(action, m, pointer * m + i);
        }
    }

    /**
     * Returns the index of the node containing the specified information in {@link ParentVector#vector}.
     * @param data the information contained in the node to search
     * @return the index of the searched node
     * @throws NoSuchElementException if no node containing the specified information is present
     */
    private int find(T data) throws NoSuchElementException {
        for (int i = 0; i < vector.size(); i++) {
            if (vector.get(i).node.getData().equals(data))
                return i;
        }
        throw new NoSuchElementException("Could not find node '" + data + "'.");
    }

    /**
     * This inner class is used as a record by the {@link ParentVector} class to associate each node
     * with its parent index.
     */
    private class ParentVectorEntry {
        Node<T> node;
        Integer parentIndex;

        public ParentVectorEntry(Node<T> node, Integer parentIndex) {
            this.node = node;
            this.parentIndex = parentIndex;
        }
    }
}

