package tree;

import tree.linked.BinaryLinkedTree;
import tree.linked.TreeDeleteMode;

public class Test {
    public static void main(String[] args) {
        BinaryLinkedTree<Character> tree = new BinaryLinkedTree<>('A');
        tree.insert('B');
        tree.insert('C');
        tree.insert('D');
        tree.insert('E');
        tree.insert('F');
        tree.insert('G');
        tree.setDeleteMode(TreeDeleteMode.CHILDREN_TO_RANDOM);
        tree.delete('B');
        tree.print();
    }
}
