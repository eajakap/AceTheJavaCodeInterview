package org.educative.modules.module2.queues.puzzles;

import java.util.*;

public class MovingAverage {
    // Initialize queue to store window elements
    private Queue<Integer> queue;
    // Store the window size
    private int windowSize;
    // Running sum for O(1) average calculation
    private int windowSum;

    public MovingAverage(int size) {
        this.queue = new LinkedList<>();
        this.windowSize = size;
        this.windowSum = 0;
    }

    public double next(int val) {
        // Add new value to the queue
        queue.add(val);
        // Update the running sum
        windowSum += val;
        // If queue exceeds window size, remove the oldest element
        if (queue.size() > windowSize) {
            // Remove front element and subtract from sum
            int removed = queue.poll();
            windowSum -= removed;
        }
        // Return the average of current window elements
        return (double) windowSum / queue.size();
    }

    public static void main(String[] args) {
        int[][] valueSets = {
                {4, 8, 6, 10},
                {1, 2, 3, 4, 5, 6},
                {7, -3, 5, 0},
                {-10, 20, -30, 40, -50},
                {100000, -100000, 50000, -50000, 0}
        };

        int[] sizes = {2, 5, 1, 4, 3};

        String[] descriptions = {
                "Window size 2 with positive values",
                "Window size 5, more values than window",
                "Window size 1, each value is its own average",
                "Window size 4 with mixed positive/negative",
                "Window size 3 with extreme values"
        };

        int y = 1;
        for (int t = 0; t < valueSets.length; t++) {
            // Create a new MovingAverage instance for each test case
            MovingAverage movingAvg = new MovingAverage(sizes[t]);
            List<Double> results = new ArrayList<>();

            // Process each value in the stream
            for (int val : valueSets[t]) {
                double result = movingAvg.next(val);
                results.add(Math.round(result * 100000.0) / 100000.0);
            }

            System.out.println(y++ + ".\tInput values: " + Arrays.toString(valueSets[t]));
            System.out.println("\tWindow size: " + sizes[t]);
            System.out.println("\tResults after each next() call: " + results);
            System.out.println("-".repeat(100));
        }
    }
}