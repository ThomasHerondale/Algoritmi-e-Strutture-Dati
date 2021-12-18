package sorting;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        Integer[] array = new Integer[] {
                11, 9, 4, 6, 2, 1, 1, 1, 4, 13, 9, 12, 11, 9, 3, 3, 0, 6
        };
        System.out.println(Arrays.toString(array));
        Sorter.mergeSort(array);
        System.out.println(Arrays.toString(array));
    }

}
