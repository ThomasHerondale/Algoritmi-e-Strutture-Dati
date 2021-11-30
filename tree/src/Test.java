import java.util.Random;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        var rng = new Random();
        var input = new Scanner(System.in);
        BinarySearchTree<Character> bst = new BinarySearchTree<>(rng.nextInt(0, 100), 'c' );
        for (var i = 0; i < 11; i++) {
            bst.insert(new Node<>(rng.nextInt(0, 100), 'a'));
        }
        bst.print();
        bst.delete(Integer.parseInt(input.nextLine()));
        bst.print();
    }
}
