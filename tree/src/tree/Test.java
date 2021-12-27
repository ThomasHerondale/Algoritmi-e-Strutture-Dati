package tree;


import tree.linked.SimpleLinkedTree;
import tree.linked.TreeDeleteMode;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Node<Character>> nodeList = new ArrayList<>();
        nodeList.add(new Node<>('A'));
        nodeList.add(new Node<>('B'));
        nodeList.add(new Node<>('C'));
        nodeList.add(new Node<>('D'));
        nodeList.add(new Node<>('E'));
        nodeList.add(new Node<>('F'));
        nodeList.add(new Node<>('G'));
        nodeList.add(new Node<>('H'));
        nodeList.add(new Node<>('I'));
        nodeList.add(new Node<>('L'));
        nodeList.add(new Node<>('M'));
        nodeList.add(new Node<>('N'));
        nodeList.add(new Node<>('O'));
        List<Integer> parentList = new ArrayList<>();
        parentList.add(null);
        parentList.add(0);
        parentList.add(0);
        parentList.add(0);
        parentList.add(1);
        parentList.add(1);
        parentList.add(1);
        parentList.add(2);
        parentList.add(2);
        parentList.add(2);
        parentList.add(3);
        parentList.add(3);
        parentList.add(3);
        ParentVector<Character> tree = new ParentVector<>(nodeList, parentList);
        tree.depthFirstSearch(System.out::println);
    }
}
