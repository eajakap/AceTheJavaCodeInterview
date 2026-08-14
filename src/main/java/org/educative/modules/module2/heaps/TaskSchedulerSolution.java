package org.educative.modules.module2.heaps;

import java.util.*;

class TaskSchedulerSolution {
    public int leastInterval(char[] tasks, int n) {
        // Build frequency map
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char task : tasks) {
            freqMap.put(task, freqMap.getOrDefault(task, 0) + 1);
        }

        // Max heap ordered by frequency (highest first)
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        maxHeap.addAll(freqMap.values());

        // Cooldown queue storing {remainingCount, availableTime}
        Queue<int[]> cooldownQueue = new LinkedList<>();

        int time = 0;

        while (!maxHeap.isEmpty() || !cooldownQueue.isEmpty()) {
            time++;

            if (!maxHeap.isEmpty()) {
                int remainingCount = maxHeap.poll() - 1;

                if (remainingCount != 0) {
                    cooldownQueue.add(new int[]{remainingCount, time + n});
                }
            }

            if (!cooldownQueue.isEmpty() && cooldownQueue.peek()[1] == time) {
                int[] readyTask = cooldownQueue.poll();
                maxHeap.add(readyTask[0]);
            }
        }

        return time;
    }

    public static void main(String[] args) {
        TaskSchedulerSolution sol = new TaskSchedulerSolution();

        char[][][] testCases = {
                {{'A', 'A', 'A', 'A'}, {2}},
                {{'A', 'B', 'C', 'D', 'E'}, {0}},
                {{'X', 'X', 'X'}, {5}},
                {{'A', 'A', 'A', 'B', 'B', 'C', 'C', 'D'}, {2}},
                {{'Z', 'Z', 'Z', 'Z', 'Z'}, {0}}
        };

        String[] descriptions = {
                "Single task type with cooldown",
                "Multiple tasks with no cooldown needed",
                "One task repeated many times with large cooldown",
                "Mixed frequencies with moderate cooldown",
                "All same tasks with zero cooldown"
        };

        int y = 1;
        for (int t = 0; t < testCases.length; t++) {
            char[] taskArr = testCases[t][0];
            int n = testCases[t][1][0];
            int result = sol.leastInterval(taskArr, n);
            System.out.println(y++ + ".\tInput array: " + Arrays.toString(taskArr));
            System.out.println("\tCooldown (n): " + n);
            System.out.println("\tResult: " + result);
            System.out.println("-".repeat(100));
        }
    }
}