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

    public void setSx(Node<T> sx) {
        this.sx = sx;
    }

    public Node<T> getDx() {
        return dx;
    }

    public void setDx(Node<T> dx) {
        this.dx = dx;
    }

    public int childrenCount() {
        var counter = 0;
        if (this.dx != null)
            counter++;
        if (this.sx != null)
            counter++;
        return counter;
    }

    @Override
    public String toString() {
        return String.valueOf(key);
    }

}
