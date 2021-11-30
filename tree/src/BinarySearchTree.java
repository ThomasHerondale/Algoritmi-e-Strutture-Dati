import java.util.ArrayDeque;
import java.util.Optional;

public class BinarySearchTree<T> {
    private Node<T> root;

    public BinarySearchTree(int key, T data) {
        this.root = new Node<>(key, data);
    }

    public void insert(Node<T> newNode) {
        var parent = findParent(newNode);
        assert parent != null;
        if (newNode.getKey() <= parent.getKey())
            parent.setSx(newNode);
        else
            parent.setDx(newNode);
    }

    private Node<T> findParent(Node<T> childNode) {
        return findParent(childNode, root);
    }

    private Node<T> findParent(Node<T> childNode, Node<T> currentNode) {
        if (childNode == root)
            return null;
        if (childNode.getKey() <= currentNode.getKey()) {
            if (currentNode.getSx() == null || childNode == currentNode.getSx())
                return currentNode;
            else
                return findParent(childNode, currentNode.getSx());
        } else {
            if (currentNode.getDx() == null || childNode == currentNode.getDx())
                return currentNode;
            else
                return findParent(childNode, currentNode.getDx());
        }
    }

    /*public Node<T> delete(int key) {

    }*/

    /* TODO: non funziona se eliminiamo il minimo dell'albero -> non ci interessa perché noi lo useremo solo
       TODO: in caso di cancellazioni */
    private Node<T> previous(Node<T> node) {
        if (node.getSx() != null)
            return maximum(node);
        else {  // Il predecessore è il primo figlio destro che si incontra RISALENDO gli antenati di node
            // Finché il nodo corrente è figlio sinistro di suo padre
            var currentNode = findParent(node);
            Node<T> parent;
            while ((parent = findParent(currentNode)) != null && currentNode != parent.getSx()) {
                currentNode = findParent(currentNode);
            }
            return currentNode;
        }
    }

    // root è la radice del sottoalbero di cui si cerca il massimo
    private Node<T> maximum(Node<T> root) {
        while (root.getDx() != null)
            root = root.getDx();
        return root;
    }

    public Optional<Node<T>> search(int key) {
        var currentNode = root;
        while (currentNode != null) {
            if (currentNode.getKey() == key)
                    return Optional.of(currentNode);
            if (key <= currentNode.getKey())
                currentNode = currentNode.getSx();
            else
                currentNode = currentNode.getDx();
        }
        return Optional.empty();
    }

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
