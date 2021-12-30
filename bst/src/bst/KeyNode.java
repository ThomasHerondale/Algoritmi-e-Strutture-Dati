package bst;

import tree.Node;

import java.util.Objects;

public class KeyNode<T> extends Node<T> {
    private int key;
    // Parent?
    private KeyNode<T> sx;
    private KeyNode<T> dx;

    public KeyNode(int key, T data) {
        super(data);
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public KeyNode<T> getSx() {
        return sx;
    }

    public void setSx(KeyNode<T> sx) {
        this.sx = sx;
    }

    public KeyNode<T> getDx() {
        return dx;
    }

    public void setDx(KeyNode<T> dx) {
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

    public KeyNode<T> getOnlyChild() {
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
        KeyNode<?> node = (KeyNode<?>) o;
        return key == node.key && getData().equals(node.getData()) && Objects.equals(sx, node.sx) && Objects.equals(dx, node.dx);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, getData(), sx, dx);
    }

    @Override
    public String toString() {
        return String.valueOf(key);
    }
}
