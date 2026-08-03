package org.educative.module1.bigonotation;

public class ArrayBinarySearch {
    public static boolean binarySearch(int[] array, int value) {
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (array[mid] == value) {
                return true;
            }
            if (array[mid] > value) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] array = { -22, -15, 1, 7, 20, 35, 55 };
        System.out.println(binarySearch(array, -15)); // true
        System.out.println(binarySearch(array, 35));   // true
        System.out.println(binarySearch(array, 8888)); // false
        System.out.println(binarySearch(array, 1));    // true
    }
}
