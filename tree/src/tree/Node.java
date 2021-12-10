package tree;

import java.util.Objects;

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

    public void setKey(int key) {
        this.key = key;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
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

    public Node<T> getOnlyChild() {
        // Questo metodo verrà chiamato solo se si è verificato che il nodo abbia un unico figlio
        assert childrenCount() == 1;

        if (this.dx != null)
            return this.dx;
        else
            return this.sx;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?> node = (Node<?>) o;
        return key == node.key && data.equals(node.data) && Objects.equals(sx, node.sx) && Objects.equals(dx, node.dx);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, data, sx, dx);
    }

    @Override
    public String toString() {
        return String.valueOf(key);
    }
}
