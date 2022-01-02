package sorting;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Test {
    /*public static void main(String[] args) {
        Random rng = new Random();
        var size = rng.nextInt(20);
        System.out.println("Size is " + size);
        Integer[] array = new Integer[size];
        for (var i = 0; i < size; i++) {
            array[i] = rng.nextInt(100);
        }
        System.out.println(Arrays.toString(array));
        Sorter.insertionSort(array);
        System.out.println(Arrays.toString(array));
    }*/

    /*public static void main(String[] args) {
        Random rng = new Random();
        var size = rng.nextInt(20);
        System.out.println("Size is " + size);
        Integer[] array = new Integer[size];
        int max = 0;
        for (var i = 0; i < size; i++) {
            int n = rng.nextInt(20);
            array[i] = n;
            if (n > max)
                max = n;
        }
        System.out.println(Arrays.toString(array));
        Sorter.integerSort(array, max);
        System.out.println(Arrays.toString(array));
    }*/

    public static void main(String[] args) {
        String[] array = new String[] {
                "abaco", "antonio", "ZEBRA", "ezecHiele", "antonio", "bolero", "RAVEL"
        };
        Sorter.bucketSort(array);
        System.out.println(Arrays.toString(array));
    }

}
