package tree;

import tree.linked.LinkedBinaryTree;
import tree.linked.TreeDeleteMode;

public class Test {
    public static void main(String[] args) {
        LinkedBinaryTree<Character> tree = new LinkedBinaryTree<>('A');
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
