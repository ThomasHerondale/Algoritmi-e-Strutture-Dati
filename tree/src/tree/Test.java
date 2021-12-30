package tree;


public class Test {
    public static void main(String[] args) {
        Character[] a = new Character[] {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'L', 'M', 'N', 'O'
        };
        EnforcedPositionalVector<Character> tree = new EnforcedPositionalVector<>(a);
        tree.depthFirstSearch(System.out::println);
    }
}
