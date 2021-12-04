package bst;

import bst.avl.AVLTree;
import bst.avl.BalancedNode;

import java.util.Random;
import java.util.Scanner;

// FUNZIONA: 0, 1, 2, 3, 5465

public class Test {
    public static void main(String[] args) {
        var rng = new Random(5465);
        var input = new Scanner(System.in);
        AVLTree<Character> bst = new AVLTree<>(rng.nextInt(0, 100), 'c' );
        System.out.println("Radice " + bst.getRoot());
        for (var i = 0; i < 11; i++) {
            var n = rng.nextInt(0, 100);
            System.out.println("Inserito " + n);
            bst.insert(new BalancedNode<>(n, 'a'));
        }
        bst.print();
    }
}
