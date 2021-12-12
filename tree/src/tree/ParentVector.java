package tree;

import java.util.*;

public class ParentVector<T> implements Tree<T> {
    private List<ParentVectorEntry> vector;

    public ParentVector(List<KeyNode<T>> nodeList, List<Integer> parentlist) throws IllegalArgumentException {
        if (nodeList.size() != parentlist.size())
            throw new IllegalArgumentException("Number of nodes is not equal to number of parents");
        vector = new ArrayList<>(nodeList.size());
        for (var i = 0; i < nodeList.size(); i++) {
            vector.add(new ParentVectorEntry(nodeList.get(i), parentlist.get(i)));
        }
        vector = Collections.unmodifiableList(vector);
    }

    @Override
    public void insert(int key, T data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public KeyNode<T> delete(int key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<KeyNode<T>> search(int key) {
        try {
            return Optional.of(vector.get(find(key)).node);
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public Optional<KeyNode<T>> findParent(int childKey) throws NoSuchElementException {
        var index = find(childKey);
        ParentVectorEntry child = vector.get(index);
        var parent = vector.get(child.parentIndex).node;
        if (parent != null)
            return Optional.of(parent);
        else
            return Optional.empty();
    }

    public List<KeyNode<T>> findChildren(int parentKey) {
        var children = new ArrayList<KeyNode<T>>();
        for (var entry : vector) {
            var entryParent = vector.get(entry.parentIndex).node;
            if (entryParent.getKey() == parentKey)
                children.add(entry.node);
        }
        return children;
    }

    private int find(int key) throws NoSuchElementException {
        for (int i = 0; i < vector.size(); i++) {
            if (vector.get(i).node.getKey() == key)
                return i;
        }
        throw new NoSuchElementException("Could not find node '" + key + "'.");
    }

    public class ParentVectorEntry {
        KeyNode<T> node;
        int parentIndex;

        public ParentVectorEntry(KeyNode<T> node, int parentIndex) {
            this.node = node;
            this.parentIndex = parentIndex;
        }
    }
}

