package sorting;

import java.util.Arrays;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Integer[] array = new Integer[] {
                10, 1, 0, 15, 18, 13, 9, 4, 3, 3, 3, 1, 0, 11, 0, 16, 7, 0
        };
        System.out.println(Arrays.toString(array));
        Sorter.mergeSort(array);
        System.out.println(Arrays.toString(array));
    }


}
