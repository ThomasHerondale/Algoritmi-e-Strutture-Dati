public class Test {
    public static void main(String[] args) {
        var bst = new BinarySearchTree<>(3, 'c');
        bst.insert(new Node<>(1, 'a'));
        bst.print();
    }
}
