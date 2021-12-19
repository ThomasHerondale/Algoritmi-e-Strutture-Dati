package sorting;

public class Sorter {
    /**
     * Don't let anyone instantiate this class.
     */
    private Sorter() {

    }

    @SuppressWarnings({"rawtypes", "unchecked", "ManualArrayCopy"})
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

    @SuppressWarnings({"rawtypes", "unchecked"})
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

    @SuppressWarnings({"unchecked", "rawtypes"})
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

    public static void mergeSort(Object[] array) throws ClassCastException {
        mergeSort(array, 0, array.length - 1);
    }

    private static void mergeSort(Object[] array, int f, int l) {
        if (f >= l)
            return;
        int mid = (f + l) / 2;
        mergeSort(array, f, mid);
        mergeSort(array, mid + 1, l);
        merge(array, f, mid, l);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void merge(Object[] array, int f1, int l1, int l2) {
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
}
