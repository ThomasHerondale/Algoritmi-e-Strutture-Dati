package bst;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Optional;

@SuppressWarnings("UnusedReturnValue")
public class BinarySearchTree<T> {
    private Node<T> root;

    public BinarySearchTree(int key, T data) {
        this.root = new Node<>(key, data);
    }

    protected BinarySearchTree(Node<T> root) {
        this.root = root;
    }

    protected Node<T> getRoot() {
        return root;
    }

    protected void setRoot(Node<T> root) {
        this.root = root;
    }

    // Ritorna il padre del nuovo nodo (l'unico nodo che questo metodo modifica)
    public Node<T> insert(Node<T> newNode) {
        var parent = findParent(newNode);
        assert parent != null;
        if (newNode.getKey() <= parent.getKey())
            parent.setSx(newNode);
        else
            parent.setDx(newNode);
        return parent;
    }

    protected Node<T> findParent(Node<T> childNode) {
        return findParent(childNode, root);
    }

    protected Node<T> findParent(Node<T> childNode, Node<T> currentNode) {
        if (childNode == root)
            return null;
        if (childNode.getKey() <= currentNode.getKey()) {
            if (currentNode.getSx() == null || childNode.equals(currentNode.getSx()))
                return currentNode;
            else
                return findParent(childNode, currentNode.getSx());
        } else {
            if (currentNode.getDx() == null || childNode.equals(currentNode.getDx()))
                return currentNode;
            else
                return findParent(childNode, currentNode.getDx());
        }
    }

    // Ritorna il nodo appena cancellato
    public Node<T> delete(int key) throws NoSuchElementException {
        var toDeleteOpt = search(key);
        return delete(toDeleteOpt.orElseThrow(NoSuchElementException::new));
    }

    private Node<T> leafDelete(Node<T> toDelete) {
        var parent = findParent(toDelete);
        // Non possiamo cancellare la radice dentro al metodo che cancella foglie!
        assert parent != null;
        // toDelete non è più figlio di suo padre
        if (toDelete.getKey() <= parent.getKey())
            parent.setSx(null);
        else
            parent.setDx(null);
        return toDelete;
    }

    protected Node<T> delete(Node<T> toDelete) {
        if (toDelete.childrenCount() == 0)
            return leafDelete(toDelete);
        else if (toDelete.childrenCount() == 1) {
            var parent = findParent(toDelete);
            // Stiamo cancellando la radice?
            if (parent == null) {
                var oldRoot = root;
                root = root.getOnlyChild();
                return oldRoot;
            }
            if (toDelete.getKey() <= parent.getKey())
                parent.setSx(toDelete.getOnlyChild());
            else
                parent.setDx(toDelete.getOnlyChild());
            return toDelete;
        } else {
            var previous = previous(toDelete);
            assert previous != null;    // Studiare il caso in cui previous è null!
            // Il predecessore dovrebbe avere, al più, un unico figlio
            assert previous.childrenCount() <= 1;
            delete(previous);
            copy(previous, toDelete);
            return toDelete;
        }
    }

    private Node<T> previous(Node<T> node) {
        if (node.getSx() != null)
            return maximum(node.getSx());
        else {
            var currentNode = findParent(node);
            Node<T> parent;
            while ((parent = findParent(currentNode)) != null && currentNode != parent.getSx()) {
                currentNode = findParent(currentNode);
            }
            return findParent(currentNode);
        }
    }

    // root è la radice del sottoalbero di cui si cerca il massimo
    private Node<T> maximum(Node<T> root) {
        while (root.getDx() != null)
            root = root.getDx();
        return root;
    }

    private void copy(Node<T> source, Node<T> target) {
        target.setKey(source.getKey());
        target.setData(source.getData());
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
