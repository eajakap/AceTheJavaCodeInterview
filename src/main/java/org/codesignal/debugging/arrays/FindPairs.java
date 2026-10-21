package org.codesignal.debugging.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map.Entry;

public class FindPairs {
    public static List<Entry<Integer, Integer>> findPairs(int[] numbers, int target) {
        Arrays.sort(numbers);
        int left = 0;
        int right = numbers.length - 1;
        List<Entry<Integer, Integer>> pairs = new ArrayList<>();

        while (left < right) {
            int total = numbers[left] + numbers[right];

            if (total == target) {
                pairs.add(new SimpleEntry<>(numbers[left], numbers[right]));
                left++;
                right--;
            } else if (total < target) {
                left++;
            } else {
                right--;
            }
        }
        return pairs;
    }

    public static void main(String[] args) {
        int[] numbers = {1, 3, 5, 2, 8, -2};
        List<Entry<Integer, Integer>> result = findPairs(numbers, 6);
        for (Entry<Integer, Integer> pair : result) {
            System.out.println(pair.getKey() + " " + pair.getValue());
        }
    }
}