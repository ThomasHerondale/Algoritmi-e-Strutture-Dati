package tree;

import java.util.Optional;

@SuppressWarnings("UnusedReturnValue")
public interface Tree<T> {

    void insert(int key, T data);

    // Ritorna il nodo appena cancellato
    Node<T> delete(int key);

    Optional<Node<T>> search(int key);
}
