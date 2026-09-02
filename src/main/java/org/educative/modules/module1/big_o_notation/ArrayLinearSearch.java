package org.educative.modules.module1.big_o_notation;
/*
    * Time Complexity: O(n), where n is the length of the array.
    * Space Complexity: O(1), as we are using a constant amount of space for the search operation.
 */
public class ArrayLinearSearch {
    public static boolean linearSearch(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return true;
            }
        }
        return false;
    }

    public static void main (String[] args) {
        int[] array = { 20, 35, -15, 7, 55, 1, -22 };
        System.out.println(linearSearch(array, -15)); // true
        System.out.println(linearSearch(array, 1));    // true
        System.out.println(linearSearch(array, 8888)); // false
        System.out.println(linearSearch(array, -22));  // true
    }
}
