package lesson1;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void swap (Object[] arr, int pos1, int pos2) {
        Object temp = arr[pos1];
        arr[pos1] = arr[pos2];
        arr[pos2] = temp;
    }

    public static <T> ArrayList<T> arrToList (T[] arr) {
        return new ArrayList<T>(Arrays.asList(arr));
    }

    public static void main(String[] args) {

    }

}
