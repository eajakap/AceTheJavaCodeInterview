package org.educative.module1.bigonotation;

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
