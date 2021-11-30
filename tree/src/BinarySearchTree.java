import java.util.ArrayDeque;
import java.util.NoSuchElementException;
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
    public Node<T> delete(int key) {
        // Se il nodo non esiste, lancia NoSuchElementException
        var toDelete = search(key).orElseThrow(NoSuchElementException::new);
        if (toDelete.childrenCount() == 0)
            return leafDelete(toDelete);
        else if (toDelete.childrenCount() == 1) {
            var parent = findParent(toDelete);
            // Stiamo cancellando la radice?
            if (parent == null)
                return rootDelete(root.childrenCount());
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
            if (previous.childrenCount() == 1) {
                var parent = findParent(previous);
                assert parent != null;  // Studiare il caso in cui parent è null!
                if (previous.getKey() <= parent.getKey())
                    parent.setSx(previous.getOnlyChild());
                else
                    parent.setDx(previous.getOnlyChild());
            }
            return toDelete;
        }
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

    private Node<T> rootDelete(int childrenCount) {
        if (childrenCount == 1)
            root = root.getOnlyChild();
        // TODO: e se root ha due figli?
        assert childrenCount == 1;
        return null;
    }

    // Metodo per cancellare il predecessore
    // Necessario poiché le chiavi duplicate non ci consentono di affidarci alla sola ricerca per chiave
    private Node<T> delete(Node<T> toDelete) {
        if (toDelete.childrenCount() == 0)
            leafDelete(toDelete);
        if (toDelete.childrenCount() == 1) {
            var parent = findParent(toDelete);
            if (parent == null)
                return rootDelete(root.childrenCount());
            if (toDelete.getKey() <= parent.getKey())
                parent.setSx(toDelete.getOnlyChild());
            else
                parent.setDx(toDelete.getOnlyChild());
            return toDelete;
        }
            return toDelete;
    }

    /* Non funziona se eliminiamo il minimo dell'albero -> non ci interessa perché noi lo useremo solo
       in caso di cancellazioni */
    private Node<T> previous(Node<T> node) {
        if (node.getSx() != null)
            return maximum(node.getSx());
        else {  // Il predecessore è il primo figlio destro che si incontra RISALENDO gli antenati di node
            // Finché il nodo corrente è figlio sinistro di suo padre
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
