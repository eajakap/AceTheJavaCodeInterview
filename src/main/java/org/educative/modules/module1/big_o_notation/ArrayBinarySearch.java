package org.educative.modules.module1.big_o_notation;

/* Time Complexity: O(log n), where n is the length of the array.
 * Space Complexity: O(1), as we are using a constant amount of space.
 * Given a sorted array of integers, write a function that returns true if the given integer is present in the array, and false otherwise.
 */
public class ArrayBinarySearch {
    // Sorted Array Binary Search
    public static boolean binarySearch(int[] array, int value) {
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (array[mid] == value) {
                return true;
            }
            if (array[mid] > value) {
                // left half - search if value is in the left half of the array
                high = mid - 1;
            } else {
                // right half - search if value is in the right half of the array
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
