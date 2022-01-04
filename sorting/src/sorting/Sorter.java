package sorting;

import java.util.*;
import java.util.function.ToIntFunction;

/**
 * This class implements many famous sorting algorithms, some of them being more optimal than others.
 * This class contains both comparison-based alghoritms and operation-based alghoritms, especially to sort integers or
 * elements that can be mapped to integer keys easily, such as strings.
 * Not that this class only contains static methods, therefore it doesn't declare any public constructor.
 */
@SuppressWarnings({"rawtypes", "unchecked", "ManualArrayCopy"})
public class Sorter {
    /**
     * Random number generator used for pivot selection in the {@link Sorter#quickSort} method.
     */
    private static Random rng = null;

    /**
     * Don't let anyone instantiate this class.
     */
    private Sorter() {

    }

    /**
     * Sorts the specified array using the Insertion sort algorithm, in time O(n^2) for the worst case.
     * Should the specified array be already sorted, the time complexity of this method becomes O(n).
     * @param array the array to be sorted
     * @throws ClassCastException if any of the array entries does not implement the {@link Comparable} interface
     */
    public static void insertionSort(Object[] array) throws ClassCastException {
        for (var i = 1; i < array.length; i++) {
            Comparable current = (Comparable) array[i];
            var j = 0;
            while (current.compareTo(array[j]) > 0 && j < i) {
                j++;
            }
            array[i] = array[array.length - 1];
            array[array.length - 1] = current;
            for (var k = array.length - 1; k > j; k--) {
                array[k] = array[k - 1];
            }
            array[j] = current;
        }
    }

    /**
     * Sorts the specified array using the Selection sort algorithm, in time O(n^2) for every situation.
     * @param array the array to be sorted
     * @throws ClassCastException if any of the array entries does not implement the {@link Comparable} interface
     */
    public static void selectionSort(Object[] array) throws ClassCastException {
        for (var i = 0; i < array.length; i++) {
            var minimum = i;
            for (var j = i + 1; j < array.length; j++) {
                if (((Comparable) array[minimum]).compareTo(array[j]) > 0)
                    minimum = j;
            }
            var temp = array[i];
            array[i] = array[minimum];
            array[minimum] = temp;
        }
    }

    /**
     * Sorts the specified array using the Bubble sort algorithm, in time O(n^2) for the worst case.
     * @param array the array to be sorted
     * @throws ClassCastException if any of the array entries does not implement the {@link Comparable} interface
     */
    public static void bubbleSort(Object[] array) throws ClassCastException {
        boolean swapHappened;
        do {
            swapHappened = false;
            for (var i = 0; i < array.length - 1; i++) {
                Comparable current = (Comparable) array[i];
                if (current.compareTo(array[i + 1]) > 0) {
                    array[i] = array[i + 1];
                    array[i + 1] = current;
                    swapHappened = true;
                }
            }
        } while (swapHappened);
    }

    /**
     * Sorts the specified array using the Merge sort algorithm, in time O(nlog(n)) for every situation.
     * @param array the array to be sorted
     * @throws ClassCastException if any of the array entries does not implement the {@link Comparable} interface
     */
    public static void mergeSort(Object[] array) throws ClassCastException {
        mergeSort(array, 0, array.length - 1);
    }

    private static void mergeSort(Object[] array, int f, int l) throws ClassCastException {
        if (f >= l)
            return;
        int mid = (f + l) / 2;
        mergeSort(array, f, mid);
        mergeSort(array, mid + 1, l);
        merge(array, f, mid, l);
    }

    private static void merge(Object[] array, int f1, int l1, int l2) throws ClassCastException {
        int start = f1;
        int f2 = l1 + 1;
        int mergePtr = 0;
        Object[] aux = new Object[l2 - f1 + 1];
        while (f1 <= l1 && f2 <= l2) {
            if (((Comparable) array[f1]).compareTo(array[f2]) <= 0) {
                aux[mergePtr] = array[f1];
                f1++;
            } else {
                aux[mergePtr] = array[f2];
                f2++;
            }
            mergePtr++;
        }
        while (f1 <= l1) {
            aux[mergePtr] = array[f1];
            f1++;
            mergePtr++;
        }
        while (f2 <= l2) {
            aux[mergePtr] = array[f2];
            f2++;
            mergePtr++;
        }
        for (var o : aux) {
            array[start] = o;
            start++;
        }
    }

    /**
     * Sorts the specified array using the Heap sort algorithm, in time O(nlog(n)) for every situation.
     * @param array the array to be sorted
     * @throws ClassCastException if any of the array entries does not implement the {@link Comparable} interface
     */
    public static void heapSort(Object[] array) throws ClassCastException {
        for (var i = (array.length / 2) - 1; i >= 0 ; i--) {
            heapify(array, array.length, i);
        }
        for (var i = array.length - 1; i >= 0; i--) {
            Object temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            
            heapify(array, i, 0);
        }
    }

    private static void heapify(Object[] array, int n, int nodeIndex) throws ClassCastException {
        var maxIdx = nodeIndex;
        var leftIdx = 2 * nodeIndex;
        var rightIdx = 2 * nodeIndex + 1;

        if (leftIdx < n && ((Comparable) array[leftIdx]).compareTo(array[maxIdx]) > 0)
            maxIdx = leftIdx;
        if (rightIdx < n && ((Comparable) array[rightIdx]).compareTo(array[maxIdx]) > 0)
            maxIdx = rightIdx;

        if (maxIdx != nodeIndex) {
            var temp = array[nodeIndex];
            array[nodeIndex] = array[maxIdx];
            array[maxIdx] = temp;
            heapify(array, n, maxIdx);
        }
    }

    /**
     * Sorts the specified array using a randomized version of the Quicksort alghoritm, in time O(nlog(n)) for
     * every situation.
     * @param array the array to be sorted
     * @throws ClassCastException if any of the array entries does not implement the {@link Comparable} interface
     */
    public static void quickSort(Object[] array) throws ClassCastException {
        quickSort(array, 0, array.length - 1);
    }

    private static void quickSort(Object[] array, int i, int l) {
        if (i >= l)
            return;
        var rng = getRNG();
        var pivot = rng.nextInt(i, l);
        var m = partition(array, i, l);
        quickSort(array, i, m - 1);
        quickSort(array, m + 1, l);
    }

    private static int partition(Object[] array, int i, int l) {
        var inf = i;
        var sup = l + 1;
        while (true) {
            do {
                inf++;
            } while (inf <= l && ((Comparable) array[inf]).compareTo(array[i]) < 0);
            do {
                sup--;
            } while (((Comparable) array[sup]).compareTo(array[i]) > 0);
            if (inf < sup) {
                var temp = array[inf];
                array[inf] = array[sup];
                array[sup] = temp;
            } else
                break;
        }
        var temp = array[sup];
        array[sup] = array[i];
        array[i] = temp;
        return sup;
    }

    /**
     * Sorts the specified integer sequence using the Integer sort alghoritm, also known as Counting sort.
     * Let {@code m} be the maximum integer in the array, this method has time complexity O(max(n, m)).
     * @param array the array to be sorted
     * @param max the biggest integer in the array
     */
    public static void integerSort(Integer[] array, int max) {
        int[] aux = new int[max + 1];
        Arrays.fill(aux, 0);
        for (var n : array) {
            aux[n]++;
        }
        var pointer = 0;
        for (var i = 0; i < max + 1; i++) {
            while (aux[i] > 0) {
                array[pointer] = i;
                aux[i]--;
                pointer++;
            }
        }
    }

    /**
     * Sorts the specified array using a perfectly stable version of the Bucket sort algorithm, provided that every
     * entry of the array can be mapped to an integer key via the specified function.
     * Let m be the maximum value returned by the specified function, this method has time complexity O(n+m).
     * @param array the array to be sorted
     * @param bound the maximum value returned by the specified function
     * @param function the function that maps every array element to an integer, not necessarily injective
     * @param <T> the type of the array entries and also the type of the parameter of the specified function
     */
    public static <T> void bucketSort(T[] array, int bound, ToIntFunction<T> function) {
        List<Deque<T>> aux = new ArrayList<>(bound + 1);
        for (var i = 0; i < bound + 1; i++) {
            aux.add(new ArrayDeque<>());
        }

        for (var o : array) {
            aux.get(function.applyAsInt(o)).offer(o);
        }

        var pointer = 0;
        for (var list : aux) {
            for (var o : list) {
                array[pointer] = o;
                pointer++;
            }
        }
    }

    /**
     * Sorts the specified array using the Radix sort algorithm, provided that every entry of the array
     * can be mapped to an integer key via the specified function.
     * The time complexity of this algorithm is extimated to be around O(kn), being k the average digit count
     * of all the values returned by the specified function.
     * @param array the array to be sorted
     * @param bound the maximum value returned by the specified function
     * @param function the function that maps every array element to an integer, not necessarily injective
     * @param <T> the type of the array entries and also the type of the parameter of the specified function
     */
    public static <T> void radixSort(T[] array, int bound, ToIntFunction<T> function) {
        var counter = 0;
        while (counter < 10) {
            radixHelper(array, bound, counter, function);
            counter++;
        }
    }

    private static <T> void radixHelper(T[] array, int r, int digitIdx, ToIntFunction<T> function) {
        List<Deque<T>> aux = new ArrayList<>(r);
        for (var i = 0; i < r; i++) {
            aux.add(new ArrayDeque<>());
        }
        var factor = Math.pow(10, digitIdx);

        for (var o : array) {
            var keyDigit = (int) (function.applyAsInt(o) % factor);
            aux.get(keyDigit).offer(o);
        }

        var pointer = 0;
        for (var list : aux) {
            for (var o : list) {
                array[pointer] = o;
                pointer++;
            }
        }
    }

    private static Random getRNG() {
        if (rng == null)
            rng = new Random();
        return rng;
    }
}
