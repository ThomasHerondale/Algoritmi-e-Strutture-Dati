package bst.avl;

import bst.BinarySearchTree;
import tree.KeyNode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

public class AVLTree<T> extends BinarySearchTree<T> {
    private final Deque<BalancedKeyNode<T>> path;

    public AVLTree(int key, T data) {
        super(new BalancedKeyNode<>(key, data));
        this.path = new ArrayDeque<>();
    }

    @Override
    public BalancedKeyNode<T> getRoot() {
        return (BalancedKeyNode<T>) super.getRoot();
    }

    @Override
    public void insert(int key, T data) {
        insert(new BalancedKeyNode<>(key, data));
    }

    @Override
    public BalancedKeyNode<T> insert(KeyNode<T> newNode) {
        BalancedKeyNode<T> parent = (BalancedKeyNode<T>) super.insert(newNode);
        // Ricalcola i fattori di bilanciamento lungo il cammino percorso per l'inserimento
        path.push(parent);
        while (!path.isEmpty()) {
            BalancedKeyNode<T> currentNode = path.pop();
            currentNode.updateBalanceFactor();
            // Se il nodo è sbilanciato, ribilancialo
            if (Math.abs(currentNode.getBalanceFactor()) >= 2)
                rebalance(currentNode);
        }
        return parent;
    }

    @Override
    protected KeyNode<T> findParent(KeyNode<T> childNode) {
        if (childNode == getRoot())
            return null;
        BalancedKeyNode<T> currentNode = getRoot();
        while (true) {
            path.push(currentNode);
            if (childNode.getKey() <= currentNode.getKey()) {
                if (currentNode.getSx() == null || childNode.equals(currentNode.getSx()))
                    return currentNode;
                else
                    currentNode = (BalancedKeyNode<T>) currentNode.getSx();
            } else {
                if (currentNode.getDx() == null || childNode.equals(currentNode.getDx()))
                    return currentNode;
                else
                    currentNode = (BalancedKeyNode<T>) currentNode.getDx();
            }
        }
    }

    @SuppressWarnings("IfStatementWithIdenticalBranches")
    private void rebalance(BalancedKeyNode<T> criticalNode) {
        System.out.println(criticalNode + " è sbilanciato!");
        // Il nodo è sbilanciato a sinistra?
        if (criticalNode.getBalanceFactor() > 0) {
            BalancedKeyNode<T> sx = (BalancedKeyNode<T>) criticalNode.getSx();
            if (sx.getBalanceFactor() >= 0) {  // SX -- SX
                rotateRight(criticalNode);
            } else {    // DX -- SX
                rotateLeft(criticalNode.getSx());
                rotateRight(criticalNode);
            }
        } else {    // Il nodo è sbilanciato a destra
            BalancedKeyNode<T> dx = (BalancedKeyNode<T>) criticalNode.getDx();
            if (dx.getBalanceFactor() >= 0) {    // SX -- DX
                rotateRight(criticalNode.getDx());
                rotateLeft(criticalNode);
            } else {    // DX -- DX
                rotateLeft(criticalNode);
            }
        }

        System.out.println("--------");
        this.print();
        System.out.println("--------");

        // Controlla che il nodo si sia bilanciato davvero
        assert Math.abs(criticalNode.getBalanceFactor()) <= 2;
    }

    private void rotateLeft(KeyNode<T> pivot) {
        var parent = findParent(pivot);
        var child = pivot.getDx();
        if (pivot == getRoot())
            setRoot(child);
        // Non dobbiamo occuparci del padre del perno, se il perno è la radice
        if (parent != null) {
            if (pivot.getKey() <= parent.getKey())
                parent.setSx(child);
            else
                parent.setDx(child);
        }
        pivot.setDx(child.getSx());
        child.setSx(pivot);
    }

    private void rotateRight(KeyNode<T> pivot) {
        var parent = findParent(pivot);
        var child = pivot.getSx();
        if (pivot == getRoot())
            setRoot(child);
        if (parent != null) {
            if (pivot.getKey() <= parent.getKey())
                parent.setSx(child);
            else
                parent.setDx(child);
        }
        pivot.setSx(child.getDx());
        child.setDx(pivot);
    }

    @Override
    public KeyNode<T> delete(int key) throws NoSuchElementException {
        var toDelete = search(key).orElseThrow(NoSuchElementException::new);
        var parent = findParent(toDelete);
        // Tieni traccia del cammino fino al nodo cancellato, a meno che non abbiamo cancellato la radice
        if (parent != null)
            pathTo(parent.getKey());
        else
            assert path.isEmpty();
        super.delete(toDelete);

        // Ricalcola i fattori di bilanciamento lungo il cammino
        while (!path.isEmpty()) {
            BalancedKeyNode<T> currentNode = path.pop();
            currentNode.updateBalanceFactor();
            // Se il nodo è sbilanciato, ribilancialo
            if (Math.abs(currentNode.getBalanceFactor()) >= 2)
                rebalance(currentNode);
        }

        return toDelete;
    }

    private void pathTo(int key) {
        assert path.isEmpty();
        var currentNode = getRoot();
        while (currentNode != null) {
            path.push(currentNode);
            if (currentNode.getKey() == key)
                return;
            if (key <= currentNode.getKey())
                currentNode = (BalancedKeyNode<T>) currentNode.getSx();
            else
                currentNode = (BalancedKeyNode<T>) currentNode.getDx();
        }
    }
}
