package tree.linked;

import tree.Node;
import tree.Tree;

import java.util.*;

/**
 * This class provides a linked implementation for the {@link Tree} interface, specifically for a generic tree with
 * no constraint on the number of children of any node, nor assurance of the number of children being constant from
 * node to node. Note that the behavior of the {@link SimpleLinkedTree#delete(Object)} method is customizable by the
 * user in the ways specified by the {@link TreeDeleteMode}.
 * @param <T> the type of the data contained in the nodes
 */
public class SimpleLinkedTree<T> implements Tree<T> {
    /**
     * The root node of this tree.
     */
    private final LinkedNode<T> root;
    /**
     * The behaviour of the {@link Tree#delete(Object)} method of this tree.
     */
    private TreeDeleteMode deleteMode;

    /**
     * Constructs a {@code SimpleBinaryTree} only composed of a root node containing the specified data. The created
     * tree will not support any removal operation, unless its behavior is changed using the {@code setDeleteMode}
     * method.
     * @param data the data that will be contained by the root of this tree
     * @implNote This constructor sets the {@link SimpleLinkedTree#deleteMode} of this tree to
     * {@link TreeDeleteMode#UNSUPPORTED}, meaning that the attempt to delete any node in this tree will result
     * in an {@link UnsupportedOperationException}. This behavior may be modified calling the
     * {@link BinaryLinkedTree#setDeleteMode(TreeDeleteMode)} method
     */
    public SimpleLinkedTree(T data) {
        this.root = new LinkedNode<>(data);
        this.deleteMode = TreeDeleteMode.UNSUPPORTED;
    }

    /**
     *  Constructs a {@code BinaryTree} only composed of a root node containing the specified data, setting
     *  the behavior of the {@link Tree#delete(Object)} method to the specified one.
     * @param data the data that will be contained by the root of this tree
     * @param deleteMode the behaviour of the {@code delete(Object)} method of this tree
     */
    public SimpleLinkedTree(T data, TreeDeleteMode deleteMode) {
        this.root = new LinkedNode<>(data);
        this.deleteMode = deleteMode;
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
        Queue<LinkedNode<T>> queue = new ArrayDeque<>();
        queue.offer(root);
        // Breadth-first visit
        while (!queue.isEmpty()) {
            var current = queue.poll();
            if (current.childrenNumber() == 0)
                current.addChildren(new LinkedNode<>(data));
            else {
                for (var child : current.getChildren()) {
                    queue.offer(child);
                }
            }
        }
    }

    /**
     * Creates and inserts a node containing the specified information in this tree, as one of the children
     * of the specified parent.
     * @param parentData the information contained in the parent node
     * @param childData the information that will be contained in the new node
     * @throws NoSuchElementException if no node containing the specified information is present
     */
    public void insertChild(T parentData, T childData) throws NoSuchElementException {
        var node = search(parentData).orElseThrow(NoSuchElementException::new);
        if (node instanceof LinkedNode<T> parent) {
            parent.addChildren(new LinkedNode<>(childData));
        } else
            throw new ClassCastException("Parent node is not an instance of " + LinkedNode.class);
    }

    /**
     * Deletes the node containing the specified information from this tree, according to the behavior specified
     * by the {@link SimpleLinkedTree#deleteMode} member.
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
    public Node<T> delete(T data) throws UnsupportedOperationException, NoSuchElementException {
        var node = search(data).orElseThrow(NoSuchElementException::new);
        if (node instanceof LinkedNode<T> toDelete) {
            switch (deleteMode) {
                case UNSUPPORTED -> throw new UnsupportedOperationException(
                        "Delete mode is set to UNSUPPORTED for this tree.");
                case LEAVES_ONLY -> {
                    if (toDelete.childrenNumber() == 0)
                        deleteHelper(toDelete);
                    else
                        throw new UnsupportedOperationException("Delete mode is set to LEAF_ONLY: attempt to" +
                                " delete a non-leaf node.");
                }
                case SUBTREE -> deleteHelper(toDelete);
            }
        } else
            throw new ClassCastException("Node to delete is not an instance of " + LinkedNode.class);
        // This should never be reached
        return null;
    }

    /**
     * A delete helper function that simply removes the specified node from this tree.
     */
    private void deleteHelper(LinkedNode<T> toDelete)
            throws UnsupportedOperationException, IllegalArgumentException, IllegalStateException {
        var parent = findParent(toDelete);
        if (parent != null) {
            parent.getChildren().remove(toDelete);
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
        Deque<LinkedNode<T>> stack = new ArrayDeque<>();
        stack.push(root);
        if (root.getData().equals(data))
            return Optional.of(root);
        // in-depth visit
        while (!stack.isEmpty()) {
            var current = stack.pop();
            for (var node : current.getChildren()) {
                if (node.getData().equals(data))
                    return Optional.of(node);
                else
                    stack.push(node);
            }
        }
        return Optional.empty();
    }

    @Override
    public void print() {
        Deque<LinkedNode<T>> stack = new ArrayDeque<>();
        System.out.println("Root node is " + root);
        stack.push(root);
        // in-depth visit
        while (!stack.isEmpty()) {
            var current = stack.pop();
            for (var node : current.getChildren()) {
                System.out.println(current + " has child " + node);
                stack.push(node);
            }
        }
    }

    /**
     * Returns the parent of the specified node.
     * @return the parent of the specified node, or null if the specified node is the root node
     */
    private LinkedNode<T> findParent(LinkedNode<T> childNode) {
        Deque<LinkedNode<T>> stack = new ArrayDeque<>();
        stack.push(root);
        // in-depth visit
        while (!stack.isEmpty()) {
            var current = stack.pop();
            for (var node : current.getChildren()) {
                if (node == childNode)
                    return current;
                stack.push(node);
            }
        }
        return null;
    }
}
