package tree.linked;

import tree.Node;
import tree.Tree;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * This class provides a linked representation of a binary tree. This particular implementation supports a large
 * variety of operations, going even further than those defined in the {@link Tree} interface, for which this class
 * still provides a more specific linked implementation.
 * @param <T> the type of the data contained in the nodes
 */
public class BinaryLinkedTree<T> implements Tree<T> {
    /**
     * The root node of this tree.
     */
    private final BinaryLinkedNode<T> root;
    /**
     * The behaviour of the {@link Tree#delete(Object)} method of this tree.
     */
    private TreeDeleteMode deleteMode;

    /**
     * Constructs a {@code LinkedBinaryTree} only composed of a root node containing the specified data.
     * @param data the data that will be contained by the root of this tree
     * @implNote This constructor sets the {@link BinaryLinkedTree#deleteMode} of this tree to
     * {@link TreeDeleteMode#UNSUPPORTED}, meaning that the attempt to delete any node in this tree will result
     * in an {@link UnsupportedOperationException}. This behavior may be modified calling the
     * {@link BinaryLinkedTree#setDeleteMode(TreeDeleteMode)} method
     */
    public BinaryLinkedTree(T data) {
        this(data, TreeDeleteMode.UNSUPPORTED);
    }

    /**
     *  Constructs a {@code LinkedBinaryTree} only composed of a root node containing the specified data, setting
     *  the behavior of the {@link Tree#delete(Object)} method to the specified one.
     * @param data the data that will be contained by the root of this tree
     * @param deleteMode the behaviour of the {@code delete(Object)} method of this tree
     */
    public BinaryLinkedTree(T data, TreeDeleteMode deleteMode) {
        this.root = new BinaryLinkedNode<>(data);
        this.deleteMode = deleteMode;
    }

    /**
     * Returns the root node of this tree.
     * @return the root of this tree.
     */
    public BinaryLinkedNode<T> getRoot() {
        return root;
    }

    /**
     * Sets the behavior of the {@link Tree#delete(Object)} method to the specified one.
     * @param deleteMode the desired behavior for the {@code delete} method.
     */
    public void setDeleteMode(TreeDeleteMode deleteMode) {
        this.deleteMode = deleteMode;
    }

    /**
     * Creates and inserts a node containing the specified information in this tree, as a leaf node.
     * @param data the information that will be contained in the inserted node
     * @implNote Note that this method is not the recommended way of adding a specific node a child of a specific
     * parent node. For this purpose, the method {@link BinaryLinkedTree#insertChild(Object, Object)} should
     * be used.
     * Also note that there is no assurance that the node will be inserted as the deepest leaf of the tree
     */
    @Override
    public void insert(T data) {
        Deque<BinaryLinkedNode<T>> stack = new ArrayDeque<>();
        stack.push(root);
        // in-depth visit
        while (!stack.isEmpty()) {
            var current = stack.pop();
            if (current.getSx() != null)
                stack.push(current.getSx());
            else {
                current.setSx(new BinaryLinkedNode<>(data));
                return;
            }
            if (current.getDx() != null)
                stack.push(current.getDx());
            else {
                current.setDx(new BinaryLinkedNode<>(data));
                return;
            }
        }

    }

    /**
     * Creates and inserts a node containing the specified information in this tree, as the leftmost possible
     * child of the node containing the specified data.
     * @param parentData the information contained in the parent node
     * @param childData the information that will be contained in the new child
     * @throws NoSuchElementException if no node containing the specified information is present
     * @throws IllegalArgumentException if the specified parent already has two children
     * @implNote Inserting the new node as the leftmost possible child should be interpreted as follows:
     * if the parent node already has a left child, then this method will try to insert such new in node
     * as the right child of the parent, eventually resulting in an {@link IllegalArgumentException} in the cases
     * specified above. Of course, if the parent node does not have a left child, then this method will
     * simply put the new node in that place straight away.
     */
    public void insertChild(T parentData, T childData) throws NoSuchElementException, IllegalArgumentException {
        var node = search(parentData).orElseThrow(NoSuchElementException::new);
        if (node instanceof BinaryLinkedNode<T> parent) {
            if (parent.getSx() == null)
                parent.setSx(new BinaryLinkedNode<>(childData));
            else if (parent.getDx() == null)
                parent.setDx(new BinaryLinkedNode<>(childData));
            else
                throw new IllegalArgumentException("Designed parent '" + parent + "' already has two children.");
        } else
            throw new ClassCastException("Parent node is not an instance of " + BinaryLinkedNode.class);
    }

    /**
     * Deletes the node containing the specified information from this tree, according to the behavior specified
     * by the {@link BinaryLinkedTree#deleteMode} member.
     * @param data the information contained in the node to remove
     * @return the eventual deleted node
     * @throws UnsupportedOperationException if the {@code deleteMode} of this tree is set to
     * {@link TreeDeleteMode#UNSUPPORTED}, or in case of attempt of removal of a non-leaf node whilst
     * {@code deleteMode} is set to {@link TreeDeleteMode#LEAVES_ONLY}
     * @throws NoSuchElementException if no node containing the specified information is present
     * @throws IllegalArgumentException in case of attempt of removal of the root node of this tree whilst the
     * {@code deleteMode} of this tree is set to {@link TreeDeleteMode#SUBTREE}
     */
    @Override
    public Node<T> delete(T data)
            throws UnsupportedOperationException, NoSuchElementException, IllegalArgumentException {
        var node = search(data).orElseThrow(NoSuchElementException::new);
        if (node instanceof BinaryLinkedNode<T> toDelete) {
            switch (deleteMode) {
                case UNSUPPORTED -> throw new UnsupportedOperationException(
                        "Delete mode is set to UNSUPPORTED for this tree.");
                case LEAVES_ONLY -> {
                    if (toDelete.getSx() != null || toDelete.getDx() != null)
                        throw new UnsupportedOperationException("Delete mode is set to LEAF_ONLY: attempt to" +
                                " delete a non-leaf node.");
                    else
                        deleteHelper(toDelete);
                }
                case CHILDREN_TO_RANDOM -> {
                    var sx = toDelete.getSx();
                    var dx = toDelete.getDx();
                    deleteHelper(toDelete);
                    if (sx != null)
                        insert(sx.getData());
                    if (dx != null)
                        insert(dx.getData());
                }
                case SUBTREE -> deleteHelper(toDelete);
            }
        } else
            throw new ClassCastException("Node to delete is not an instance of " + BinaryLinkedNode.class);
        // This should never be reached
        return null;
    }

    private void deleteHelper(BinaryLinkedNode<T> toDelete)
            throws UnsupportedOperationException, IllegalArgumentException, IllegalStateException {
        var parent = findParent(toDelete);
        if (parent != null) {
            if (parent.getDx() == toDelete)
                parent.setDx(null);
            else
                parent.setSx(null);
        } else {
            if (deleteMode == TreeDeleteMode.LEAVES_ONLY)
                throw new UnsupportedOperationException("Delete mode is set to LEAF_ONLY: attempt to" +
                        " delete a non-leaf node.");
            else if (deleteMode == TreeDeleteMode.SUBTREE)
                throw new IllegalArgumentException("Delete mode is set to SUBTREE: attempt to delete the root node.");
            else
                throw new IllegalStateException("Could not find parent of node " + toDelete);
        }
    }

    @Override
    public Optional<Node<T>> search(T data) {
        Deque<BinaryLinkedNode<T>> stack = new ArrayDeque<>();
        stack.push(root);
        if (root.getData().equals(data))
            return Optional.of(root);
        // in-depth visit
        while (!stack.isEmpty()) {
            var current = stack.pop();
            if (current.getSx() != null) {
                if (current.getSx().getData().equals(data))
                    return Optional.of(current.getSx());
                stack.push(current.getSx());
            }
            if (current.getDx() != null) {
                if (current.getDx().getData().equals(data))
                    return Optional.of(current.getDx());
                stack.push(current.getDx());
            }
        }
        return Optional.empty();
    }

    @Override
    public void print() {
        Deque<BinaryLinkedNode<T>> stack = new ArrayDeque<>();
        System.out.println("Root node is " + root);
        stack.push(root);
        // in-depth visit
        while (!stack.isEmpty()) {
            var current = stack.pop();
            if (current.getSx() != null) {
                System.out.println(current + " has left child " + current.getSx());
                stack.push(current.getSx());
            }
            if (current.getDx() != null) {
                System.out.println(current + " has right child " + current.getDx());
                stack.push(current.getDx());
            }
        }
    }

    private BinaryLinkedNode<T> findParent(BinaryLinkedNode<T> childNode) {
        Deque<BinaryLinkedNode<T>> stack = new ArrayDeque<>();
        stack.push(root);
        // in-depth visit
        while (!stack.isEmpty()) {
            var current = stack.pop();
            if (current.getSx() != null) {
                if (current.getSx() == childNode)
                    return current;
                stack.push(current.getSx());
            }
            if (current.getDx() != null) {
                if (current.getDx() == childNode)
                    return current;
                stack.push(current.getDx());
            }
        }
        return null;
    }
}
