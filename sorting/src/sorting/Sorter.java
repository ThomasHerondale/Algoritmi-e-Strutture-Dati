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
}
