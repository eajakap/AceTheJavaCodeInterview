package org.educative.module2.datastructures;

import java.util.ArrayList;
import java.util.Arrays;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class ArrayLinearBinarySearch {

    public static int linearSearch(int[] array, int target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                return i;
            }
        }

        return -1;
    }

    public static int binarySearch(int[] array, int value) {
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (array[mid] == value) {
                return mid;
            }
            if (array[mid] > value) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);
        }

        ArrayList<Integer> scores = new ArrayList<>(Arrays.asList(120, 135, 98, 210, 175));
        ArrayList<String> names = new ArrayList<>(Arrays.asList("Alice", "Bob", "Charlie"));

        System.out.println("Scores array is: " + scores);
        System.out.println("Names array is: " + names);

        int[] scores2 = {120, 135, 98, 210, 175};
        double[] temperatures = {36.6, 37.1, 36.9, 38.2};

        System.out.println("Scores array is: " + java.util.Arrays.toString(scores2));
        System.out.println("Temperatures array is: " + java.util.Arrays.toString(temperatures));
        System.out.println("Score at index-2: " + scores2[2]);  // 70

        int target = 210;
        int index = linearSearch(scores2, target);
        if (index != -1) {
            System.out.println("Found " + target + " at index: " + index);
        } else {
            System.out.println(target + " not found in the array.");
        }
        int[] scores3 = {120, 135, 140, 210, 275};
        target = 210;
        index = Arrays.binarySearch(scores3, target); // returns 3
        System.out.println("Found " + target + " at index: " + index);
        target = 275;
        index = binarySearch(scores3, target); // returns 3
        System.out.println("Found " + target + " at index: " + index);


    }
}