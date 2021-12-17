package tree.linked;

import tree.Node;
import tree.Tree;

import java.util.*;

public class SimpleLinkedTree<T> implements Tree<T> {
    /**
     * The root node of this tree.
     */
    private final LinkedNode<T> root;

    public SimpleLinkedTree(T data) {
        this.root = new LinkedNode<>(data);
    }

    @Override
    public void insert(T data) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Insertion is only possible with the insertChild() method" +
                "in this implementation.");
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


    @Override
    public Node<T> delete(T data) throws UnsupportedOperationException, NoSuchElementException {
        return null;
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
                System.out.println(current + "has child " + node);
                stack.push(node);
            }
        }
    }
}
