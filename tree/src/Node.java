public class Node<T> {
    private int key;
    private T data;
    // Parent?
    private Node<T> sx;
    private Node<T> dx;

    public int getKey() {
        return key;
    }

    public Node<T> getSx() {
        return sx;
    }

    public void setSx(Node<T> sx) {
        this.sx = sx;
    }

    public Node<T> getDx() {
        return dx;
    }

    public void setDx(Node<T> dx) {
        this.dx = dx;
    }
}
