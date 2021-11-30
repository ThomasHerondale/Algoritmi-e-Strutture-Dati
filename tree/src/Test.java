public class Test {
    public static void main(String[] args) {
        var bst = new BinarySearchTree<>(3, 'c');
        bst.insert(new Node<>(2, 'c'));
        bst.insert(new Node<>(1, 'a'));
        bst.insert(new Node<>(9, 'p'));
        bst.insert(new Node<>(4, 'd'));
        bst.insert(new Node<>(5, 'e'));
        bst.insert(new Node<>(10, 'l'));
        bst.insert(new Node<>(11, 'k'));
        bst.insert(new Node<>(7, 'q'));
        bst.insert(new Node<>(8, 'o'));
        bst.insert(new Node<>(6, 'j'));
        var bin = bst.delete(3);
        bst.print();
    }
}
