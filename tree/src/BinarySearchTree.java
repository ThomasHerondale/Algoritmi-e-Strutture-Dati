public class BinarySearchTree<T> {
    private Node<T> root;

    public void insert(Node<T> newNode) {
        var parent = findParent(newNode);
        if (newNode.getKey() <= parent.getKey())
            parent.setSx(newNode);
        else
            parent.setDx(newNode);
    }

    private Node<T> findParent(Node<T> node) {
        var currentNode = root;
        while (true) {
            if (node.getKey() <= currentNode.getKey()) {
                if (currentNode.getSx() == null)
                    return currentNode;
                else
                    currentNode = currentNode.getSx();
            } else {
                if (currentNode.getDx() == null)
                    return currentNode;
                else
                    currentNode = currentNode.getDx();
            }
        }
    }

    public Node<T> delete(int key) {

    }

    public boolean search(int key) {

    }

    public void print() {

    }
}
