package sorting;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Random rng = new Random();
        var size = rng.nextInt(20);
        System.out.println("Size is " + size);
        Integer[] array = new Integer[size];
        for (var i = 0; i < size; i++) {
            array[i] = rng.nextInt(100);
        }
        System.out.println(Arrays.toString(array));
        Sorter.quickSort(array);
        System.out.println(Arrays.toString(array));
    }


}
