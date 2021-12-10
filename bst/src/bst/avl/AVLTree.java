package bst.avl;

import bst.BinarySearchTree;
import tree.Node;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

public class AVLTree<T> extends BinarySearchTree<T> {
    private final Deque<BalancedNode<T>> path;

    public AVLTree(int key, T data) {
        super(new BalancedNode<>(key, data));
        this.path = new ArrayDeque<>();
    }

    @Override
    public BalancedNode<T> getRoot() {
        return (BalancedNode<T>) super.getRoot();
    }

    @Override
    public BalancedNode<T> insert(Node<T> newNode) {
        BalancedNode<T> parent = (BalancedNode<T>) super.insert(newNode);
        // Ricalcola i fattori di bilanciamento lungo il cammino percorso per l'inserimento
        path.push(parent);
        while (!path.isEmpty()) {
            BalancedNode<T> currentNode = path.pop();
            currentNode.updateBalanceFactor();
            // Se il nodo è sbilanciato, ribilancialo
            if (Math.abs(currentNode.getBalanceFactor()) >= 2)
                rebalance(currentNode);
        }
        return parent;
    }

    @Override
    protected Node<T> findParent(Node<T> childNode) {
        if (childNode == getRoot())
            return null;
        BalancedNode<T> currentNode = getRoot();
        while (true) {
            path.push(currentNode);
            if (childNode.getKey() <= currentNode.getKey()) {
                if (currentNode.getSx() == null || childNode.equals(currentNode.getSx()))
                    return currentNode;
                else
                    currentNode = (BalancedNode<T>) currentNode.getSx();
            } else {
                if (currentNode.getDx() == null || childNode.equals(currentNode.getDx()))
                    return currentNode;
                else
                    currentNode = (BalancedNode<T>) currentNode.getDx();
            }
        }
    }

    @SuppressWarnings("IfStatementWithIdenticalBranches")
    private void rebalance(BalancedNode<T> criticalNode) {
        System.out.println(criticalNode + " è sbilanciato!");
        // Il nodo è sbilanciato a sinistra?
        if (criticalNode.getBalanceFactor() > 0) {
            BalancedNode<T> sx = (BalancedNode<T>) criticalNode.getSx();
            if (sx.getBalanceFactor() >= 0) {  // SX -- SX
                System.out.println("Ruoto " + criticalNode + " a destra");
                rotateRight(criticalNode);
            } else {    // DX -- SX
                System.out.println("Composta");
                System.out.println("Ruoto " + criticalNode.getSx() + " a sinistra");
                rotateLeft(criticalNode.getSx());
                System.out.println("Ruoto " + criticalNode + " a destra");
                rotateRight(criticalNode);
            }
        } else {    // Il nodo è sbilanciato a destra
            BalancedNode<T> dx = (BalancedNode<T>) criticalNode.getDx();
            if (dx.getBalanceFactor() >= 0) {    // SX -- DX
                System.out.println("Composta");
                System.out.println("Ruoto " + criticalNode.getDx() + " a destra");
                rotateRight(criticalNode.getDx());
                System.out.println("Ruoto " + criticalNode + " a sinistra");
                rotateLeft(criticalNode);
            } else {    // DX -- DX
                System.out.println("Ruoto " + criticalNode + " a sinistra");
                rotateLeft(criticalNode);
            }
        }

        System.out.println("--------");
        this.print();
        System.out.println("--------");

        // Controlla che il nodo si sia bilanciato davvero
        assert Math.abs(criticalNode.getBalanceFactor()) <= 2;
    }

    private void rotateLeft(Node<T> pivot) {
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

    private void rotateRight(Node<T> pivot) {
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
    public Node<T> delete(int key) throws NoSuchElementException {
        var toDelete = search(key).orElseThrow(NoSuchElementException::new);
        var parent = findParent(toDelete);
        // Tieni traccia del cammino fino al nodo cancellato, a meno che non abbiamo cancellato la radice
        if (parent != null)
            pathTo(parent.getKey());
        else
            assert path.isEmpty();
        super.delete(toDelete);

        // TODO probabilmente broken, perché dopo la prima rotazione i nodi nello stack non sono più aggiornati
        // Ricalcola i fattori di bilanciamento lungo il cammino
        while (!path.isEmpty()) {
            BalancedNode<T> currentNode = path.pop();
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
                currentNode = (BalancedNode<T>) currentNode.getSx();
            else
                currentNode = (BalancedNode<T>) currentNode.getDx();
        }
    }
}
