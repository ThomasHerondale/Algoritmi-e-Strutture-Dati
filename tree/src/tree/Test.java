package tree;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        Character[] data = new Character[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M'
        };
        var tree = new EnforcedPositionalVector<>(data);
        tree.print();
    }
}
