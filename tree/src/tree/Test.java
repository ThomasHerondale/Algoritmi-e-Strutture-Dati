package tree;


import tree.linked.SimpleLinkedTree;
import tree.linked.TreeDeleteMode;

public class Test {
    public static void main(String[] args) {
        SimpleLinkedTree<Character> tree = new SimpleLinkedTree<>('A');
        tree.insert('B');
        tree.insert('C');
        tree.insertChild('C', 'D');
        tree.insert('F');
        tree.insert('C');
        tree.setDeleteMode(TreeDeleteMode.LEAVES_ONLY);
        tree.delete('C');
        tree.print();
    }
}
