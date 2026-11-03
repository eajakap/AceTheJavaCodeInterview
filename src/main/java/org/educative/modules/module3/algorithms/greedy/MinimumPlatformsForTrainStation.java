package org.educative.modules.module3.algorithms.greedy;

import java.util.Arrays;

/**
 * Problem: Minimum Number of Platforms Required for Train Station
 * Given arrival and departure times of all trains that reach a railway station, the task is to find the
 * minimum number of platforms required for the railway station so that no train waits.
 * Learn to analyze arrival and departure times and implement a step-by-step solution method suitable for coding interviews.
 * Input: Two arrays representing arrival and departure times of trains.
 * Output: Minimum number of platforms required for the railway station.
 * Example:
 * Input: arrival[] = {9:00, 9:40, 9:50, 11:00, 15:00, 18:00}
 *        departure[] = {9:10, 12:00, 11:20, 11:30, 19:00, 20:00}
 * Output: Minimum number of platforms required = 3
 * Explanation:
 * At time 9:00, train 1 arrives and occupies platform 1. At time 9:40, train 2 arrives and occupies platform 2.
 * At time 9:50, train 3 arrives and occupies platform 3. At time 11:00,
 * train 4 arrives and occupies platform 1 (train 1 has departed).
 * At time 11:20, train 3 departs, freeing up platform 3.
 * At time 11:30, train 4 departs, freeing up platform 1. At time 12:00, train 2 departs, freeing up platform 2.
 * At time 15:00, train 5 arrives and occupies platform 1. At time 18:00, train 6 arrives and occupies platform 2.
 * At time 19:00, train 5 departs, freeing up platform 1. At time 20:00, train 6 departs, freeing up platform 2.
 * The maximum number of platforms occupied at any time is 3, which occurs between 9:50 and 11:00.
 */
public class MinimumPlatformsForTrainStation {

    /**
     * Evaluates the platform requirement by comparing each train's time window with the other arrivals.
     * This direct approach takes O(n^2) time and O(1) additional space.
     */
    static class BruteForceMinPlatforms {
        /**
         * Finds the largest number of arrivals that fall within a train's arrival-to-departure interval.
         *
         * @param arrival   train arrival times, represented as HHMM integers
         * @param departure corresponding train departure times, represented as HHMM integers
         * @return the largest overlap count found
         */
        public static int findPlatform(int[] arrival, int[] departure) {

            int n = arrival.length;
            int result = 0;
            int count = 0;

            for (int index = 0; index < n; index++) {
                count = 0;
                for (int i = 1; i < n; i++) {
                    if (arrival[i] >= arrival[index] && arrival[i] <= departure[index]) {
                        count++;
                    }
                }
                if (result < count)
                    result = count;
            }
            return result;
        }

        /** Runs the brute-force example inputs and prints their calculated platform counts. */
        public static void main(String[] args){
            System.out.println("Brute Force Approach");
            //Example 1
            int arrival[] = {900, 940, 950, 1100, 1500, 1800};
            int departure[] = {910, 1200, 1120, 1130, 1900, 2000};
            int answer  = BruteForceMinPlatforms.findPlatform(arrival, departure);
            System.out.println("Minimum Number of Platforms Required for Plan1 = " + answer);


            System.out.println();

            // Example 2
            int arrival1[] = {200, 210, 300, 320, 350, 500};
            int departure1[] = {230, 240, 320, 430, 400, 520};
            int answer2 = BruteForceMinPlatforms.findPlatform(arrival1, departure1);
            System.out.println("Minimum Number of Platforms Required for Plan2 = " + answer2);

        }
    }

    /**
     * Uses sorted arrival and departure events to track the number of platforms in use.
     * The sweep takes O(n log n) time for sorting and O(1) additional space apart from the sorting implementation.
     */
    static class SortingMinPlatforms {
        /**
         * Finds the maximum number of trains present at the station at the same time.
         * The input arrays are sorted in place.
         *
         * @param arrival   train arrival times, represented as HHMM integers
         * @param departure corresponding train departure times, represented as HHMM integers
         * @return the maximum number of platforms needed
         */
        public static int findPlatform(int[] arrival, int[] departure) {
            Arrays.sort(arrival); // Sort the arrival times in ascending order
            Arrays.sort(departure); // Sort the departure times in ascending order
            int platforms = 1; // Initialize the number of platforms needed
            int maxPlatforms = 1; // Initialize the maximum number of platforms needed
            int i = 1; // Pointer for arrival times
            int j = 0; // Pointer for departure times
            while (i < arrival.length && j < departure.length) {
                if (arrival[i] <= departure[j]) {
                    // An arrival at the same time as a departure needs a separate platform.
                    platforms++; // A train has arrived before the previous one departs, so we need an additional platform
                    i++; // Move to the next arrival
                } else {
                    platforms--; // A train has departed, freeing up a platform
                    j++; // Move to the next departure
                }
                maxPlatforms = Math.max(maxPlatforms, platforms); // Update the maximum number of platforms needed
            }

            return maxPlatforms;
        }

        /** Runs the sorting-based example inputs and prints their calculated platform counts. */
        public static void main(String[] args){
            System.out.println("Sorting Approach");

            //Example 1
            int arrival[] = {900, 940, 950, 1100, 1500, 1800};
            int departure[] = {910, 1200, 1120, 1130, 1900, 2000};
            int answer  = SortingMinPlatforms.findPlatform(arrival, departure);
            System.out.println("Minimum Number of Platforms Required for Plan1 = " + answer);

            System.out.println();

            // Example 2
            int arrival1[] = {200, 210, 300, 320, 350, 500};
            int departure1[] = {230, 240, 320, 430, 400, 520};
            int answer2 = SortingMinPlatforms.findPlatform(arrival1, departure1);
            System.out.println("Minimum Number of Platforms Required for Plan2 = " + answer2);

        }

    }

    /** Runs both example implementations. */
    public static void main(String[] args) {
        BruteForceMinPlatforms.main(args);
        System.out.println();
        SortingMinPlatforms.main(args);
    }
}
