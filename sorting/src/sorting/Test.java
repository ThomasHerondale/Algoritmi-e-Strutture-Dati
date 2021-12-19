package sorting;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        Integer[] array = new Integer[] {
                7, 2, 4, 5, 3, 1, 5, 6
        };
        System.out.println(Arrays.toString(array));
        Sorter.mergeSort(array);
        System.out.println(Arrays.toString(array));
    }

}
