package tree;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Node<Character>> nodeList = new ArrayList<>();
        for (char c = 'A'; c <= 'M'; c++) {
            nodeList.add(new Node<>(c));
        }

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
        var parentVector = new ParentVector<>(nodeList, parentList);
        parentVector.print();
        System.out.println(parentVector.findChildren('C'));
        System.out.println(parentVector.findParent('A'));
        parentVector.delete('A');
    }
}
