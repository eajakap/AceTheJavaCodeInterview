package org.educative.modules.module2.heaps;

import java.util.*;

public class KthLargestElementMain {

    private PriorityQueue<Integer> topKHeap;
    private int k;

    public KthLargestElementMain() {
        this.topKHeap = new PriorityQueue<>();
        this.k = 0;
    }

    public KthLargestElementMain(int k, int[] nums) {
        // min heap of size k to store the top k largest elements
        this.topKHeap = new PriorityQueue<>();
        this.k = k;
        if (nums == null) return;

        for (int element : nums) {
            add(element);
        }
    }

    public int add(int val) {
        if (topKHeap.size() < k) {
            topKHeap.add(val);
        } else if (val > topKHeap.peek()) {
            topKHeap.poll();
            topKHeap.add(val);
        }

        return topKHeap.peek();
    }

    public static int[] findNLargestNumbers(int k, int[] array) {

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int num : array) {

            minHeap.offer(num);

            // Keep only the k largest elements
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        int[] result = new int[k];

        // Extract in descending order
        for (int i = k - 1; i >= 0; i--) {
            result[i] = minHeap.poll();
        }

        return result;
    }

    public static int kthLargestElement2(int k, int[] numbers) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for (int s : numbers) {
            maxHeap.add(s);
        }

        int size = maxHeap.size();
        for (int i = 0; k <= size && i < k - 1; i++) {
            if (!maxHeap.isEmpty()) {
                maxHeap.poll();
            }
        }

        return maxHeap.isEmpty() ? 0 : maxHeap.peek();
    }

    public static void main(String[] args) {
        KthLargestElementMain sol = new KthLargestElementMain();

        int[][][] testCases = {
                {{3, 7, 2}, {3}},
                {{10, 4, 2, 10}, {2}},
                {{5, 5, 5, 5}, {1}}, // all elements are the same, so the largest is 5
                {{1, 2, 3, 4, 5, 6}, {3}},
                {{3, 6, 9, 10, 4}, {3}},
                {{1000, 999, 1, 1, 1}, {2}},
                {{33, 55, 13, 46, 87, 87, 55, 42, 10, 34, 43, 56, 99, 100}, {2}} // second largest number is 99

        };

        int y = 1;
        for (int[][] testCase : testCases) {
            int[] nums = testCase[0];
            int k = testCase[1][0];
            int result = sol.kthLargestElement2(k, nums);
            System.out.println(y++ + ".\tInput array: " + Arrays.toString(nums) + ", k = " + k);
            System.out.println("\tResult: " + result);
            int [] result2 = sol.findNLargestNumbers(k, nums);
            System.out.println("\tResult: " + Arrays.toString(result2));
            System.out.println("-".repeat(100));
        }
    }
}

