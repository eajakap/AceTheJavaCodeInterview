package org.codesignal.debugging.arrays;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class SmallestAbsoluteDifference {
    public int smallestAbsoluteDifference(List<Integer> input) {
        if (input.size() < 2) {
            System.out.println("List must contain at least two elements.");
            return 0;
        }
        int minDiff = Integer.MAX_VALUE;
        List<Integer> list = new ArrayList<>(input); // List.of() is immutable
        Collections.sort(list);
        for (int i = 0; i < list.size() - 1; i++) {
            int diff = Math.abs(list.get(i) - list.get(i + 1));
            if (diff < minDiff) {
                minDiff = diff;
            }
        }
        return minDiff;
    }
    public static void main(String[] args) {
        SmallestAbsoluteDifference sad = new SmallestAbsoluteDifference();
        List<Integer> list = List.of(3, 8, 15, 17);
        System.out.println(sad.smallestAbsoluteDifference(list)); // Output: 2
        System.out.println(sad.smallestAbsoluteDifference(list));
    }
}
