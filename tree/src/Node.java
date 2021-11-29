public class Node<T> {
    private int key;
    private T data;
    // Parent?
    private Node<T> sx;
    private Node<T> dx;

    public Node(int key, T data) {
        this.key = key;
        this.data = data;
    }

    public int getKey() {
        return key;
    }

    public Node<T> getSx() {
        return sx;
    }

    public Node<T> getDx() {
        return dx;
    }


}
