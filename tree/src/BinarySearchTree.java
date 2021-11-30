import java.util.ArrayDeque;

public class BinarySearchTree<T> {
    private Node<T> root;

    public BinarySearchTree(int key, T data) {
        this.root = new Node<>(key, data);
    }

    public void insert(Node<T> newNode) {
        var parent = findParent(newNode);
        if (newNode.getKey() <= parent.getKey())
            parent.setSx(newNode);
        else
            parent.setDx(newNode);
    }

    private Node<T> findParent(Node<T> childNode) {
        return findParent(childNode, root);
    }

    private Node<T> findParent(Node<T> childNode, Node<T> currentNode) {
        if (childNode.getKey() <= currentNode.getKey()) {
            if (childNode == currentNode.getSx())
                return currentNode;
            else
                return findParent(childNode, currentNode.getSx());
        } else {
            if (childNode == currentNode.getDx())
                return currentNode;
            else
                return findParent(childNode, currentNode.getDx());
        }
    }

    /*public Node<T> delete(int key) {

    }*/

    /*public boolean search(int key) {

    }*/

    public void print() {
        var stack = new ArrayDeque<Node<T>>();
        stack.push(root);

        while (!stack.isEmpty()) {
            boolean isLeaf = true;
            var currentNode = stack.pop();
            if (currentNode.getSx() != null) {
                isLeaf = false;
                System.out.println("'" + currentNode + "' " + "ha figlio sx. '" + currentNode.getSx() + "'");
                stack.push(currentNode.getSx());
            }

            if (currentNode.getDx() != null) {
                isLeaf = false;
                System.out.println("'" + currentNode + "' " + "ha figlio dx. '" + currentNode.getDx() + "'");
                stack.push(currentNode.getDx());
            }

            if (isLeaf)
                System.out.println("'" + currentNode + "' è un nodo foglia.");
        }
    }
}
