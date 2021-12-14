package sorting;

import java.util.List;

/**
 * The class sorting.ListSorter contains many implementations of famous sorting alghoritms, whether their
 * performances are optimal or not.
 *
 */
public final class ListSorter<T extends Comparable<T>> {


    /**
     * Sorts a list using the Insertion Sort algorithm, in time O(n^2). Actually, if the list is already
     * partially sorted, this method reaches better performances.
     * @param list the list to be sorted
     */
    public void insertionSort(List<T> list) {
        for (var i = 1; i < list.size(); i++) {
            var current = list.get(i);
            int j = 0;
            // Trova il posto giusto per current
            while (j <= i - 1 && list.get(j).compareTo(current) <= 0)
                j++;
            // Scambia l'elemento corrente con l'ultimo
            list.set(i, list.get(list.size() - 1));
            // Fai posto per inserire current al posto giusto, shiftando gli elementi a destra
            for (var k = list.size() - 1; k > j; k--) {
                list.set(k, list.get(k - 1));

            }
            list.set(j, current);
        }
    }

    /**
     * Sorts a list using the Bubble Sort algorithm, in time O(n^2). Actually, it is far more probable
     * that this method is going to reach better performances.
     * @param list the list to be sorted
     */
    public void bubbleSort(List<T> list) {
        boolean listChanged;
        do {
            listChanged = false;
            for (var i = 0; i < list.size() - 1; i++) {
                if (list.get(i).compareTo(list.get(i + 1)) > 0) {
                    var temp = list.get(i);
                    list.set(i, list.get(i + 1));
                    list.set(i + 1, temp);
                    listChanged = true;
                }
            }
        } while (listChanged);
    }

    public void heapSort(List<T> list) {
        // TODO
    }

    public void mergeSort(T[] array) {
        mergeSort(array, 0, array.length - 1);
    }

    private void mergeSort(T[] array, int start, int end) {
        if (start >= end)
            return;
        int mid = (start + end) / 2;
        mergeSort(array, start, mid);
        mergeSort(array, mid + 1, end);
        merge(array, start, mid, end);
    }

    private void merge(
            T[] array, int firstPointer, int firstTerminator, int secondTerminator) {
        var auxArray = getGenericArrayInstance();
        int secondPointer = firstTerminator + 1;
        int auxPointer = 0;

        while (firstPointer <= firstTerminator && secondPointer <= secondTerminator) {
            if (array[firstPointer].compareTo(array[secondPointer]) <= 0) {
                auxArray[auxPointer] = array[firstPointer];
                firstPointer++;
            } else {
                auxArray[auxPointer] = array[secondPointer];
                secondPointer++;
            }
            auxPointer++;
        }
    }

    private T[] getGenericArrayInstance(int size) {

    }

    @SafeVarargs
    private T[] getGenericArrayInstance(T ...dummyValues) {
        return dummyValues;
    }

}
