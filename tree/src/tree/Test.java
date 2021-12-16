package tree;

import tree.linked.LinkedBinaryTree;
import tree.linked.TreeDeleteMode;

public class Test {
    public static void main(String[] args) {
        LinkedBinaryTree<Character> tree = new LinkedBinaryTree<>('A');
        tree.insert('B');
        tree.insert('C');
        tree.insert('D');
        tree.setDeleteMode(TreeDeleteMode.LEAVES_ONLY);
        tree.delete('A');
        tree.print();
    }
}
