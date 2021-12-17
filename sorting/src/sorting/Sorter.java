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
}
