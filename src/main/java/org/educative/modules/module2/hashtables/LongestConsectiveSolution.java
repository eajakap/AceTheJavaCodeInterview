package org.educative.modules.module2.hashtables;

import java.util.*;

class LongestConsectiveSolution {
    public int longestConsecutive(int[] nums) {
        // Convert array to a HashSet for O(1) lookups
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }
        // Track the longest streak found
        int longestStreak = 0;

        // Iterate through each unique number
        for (int num : numSet) {
            // Only start counting if num is the beginning of a sequence
            if (!numSet.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;

                // Count consecutive numbers starting from currentNum
                while (numSet.contains(currentNum + 1)) {
                    currentNum++;
                    currentStreak++;
                }

                // Update the longest streak if current is longer
                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }

        return longestStreak;
    }

    public int[] longestConsecutiveSeries(int[] nums) {
        // Convert array to a HashSet for O(1) lookups
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }
        // Track the longest streak found
        int longestStreak = 0;
        List<Integer> longestSeries = new ArrayList<>();

        // Iterate through each unique number
        for (int num : numSet) {
            // Only start counting if num is the beginning of a sequence
            if (!numSet.contains(num - 1)) {
                List<Integer> currentSeries = new ArrayList<>();
                currentSeries.add(num);
                int currentNum = num;
                int currentStreak = 1;

                // Count consecutive numbers starting from currentNum
                while (numSet.contains(currentNum + 1)) {
                    currentNum++;
                    currentStreak++;
                    currentSeries.add(currentNum);
                }

                // Update the longest streak if current is longer
                longestStreak = Math.max(longestStreak, currentStreak);
                if (currentStreak > longestSeries.size()) {
                    longestSeries = currentSeries;
                }
            }
        }

        return longestSeries.stream().mapToInt(i -> i).toArray();
    }


    public static void main(String[] args) {
        LongestConsectiveSolution sol = new LongestConsectiveSolution();

        int[][] testCases = {
                {10, 5, 12, 3, 55, 11, 13, 4},
                {},
                {7},
                {-3, -2, -1, 0, 1, 5, 6, 7, 8, 9, 10},
                {1000000000, -1000000000, 0, 999999999, -999999999}
        };

        String[] comments = {
                "Consecutive: 10,11,12,13 -> 4",
                "Empty array -> 0",
                "Single element -> 1",
                "Two sequences: -3 to 1 (5) and 5 to 10 (6) -> 6",
                "Large values, consecutive pairs -> 2"
        };

        int y = 1;
        for (int t = 0; t < testCases.length; t++) {
            int result = sol.longestConsecutive(testCases[t]);
            System.out.println(y++ + ".\tnums: " + Arrays.toString(testCases[t]));
            System.out.println("\tResult: " + result);
            System.out.println("-".repeat(100));
            int[] seriesResult = sol.longestConsecutiveSeries(testCases[t]);
            System.out.println(y++ + ".\tnums: " + Arrays.toString(testCases[t]));
            System.out.println("\tLongest Consecutive Series: " + Arrays.toString(seriesResult));
            System.out.println("-".repeat(100));
        }
    }
}