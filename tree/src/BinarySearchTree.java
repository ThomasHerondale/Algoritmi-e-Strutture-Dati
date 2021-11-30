public class BinarySearchTree<T> {
    private Node<T> root;

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

    public Node<T> delete(int key) {

    }

    public boolean search(int key) {

    }

    public void print() {

    }
}
