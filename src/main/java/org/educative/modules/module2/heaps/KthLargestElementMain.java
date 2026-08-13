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
        if (nums == null || nums.length == 0) return;
        // min heap of size k to store the top k largest elements
        this.topKHeap = new PriorityQueue<>();
        this.k = k;

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
//        KthLargestElementMain sol = new KthLargestElementMain();
//
//        int[][][] testCases = {
//                {{3, 7, 2}, {3}},
//                {{10, 4, 2, 10}, {2}},
//                {{5, 5, 5, 5}, {1}},
//                {{1, 2, 3, 4, 5, 6}, {3}},
//                {{3, 6, 9, 10, 4}, {3}},
//                {{1000, 999, 1, 1, 1}, {2}}
//        };
//
//        int y = 1;
//        for (int[][] testCase : testCases) {
//            int[] nums = testCase[0];
//            int k = testCase[1][0];
//            int result = sol.kthLargestElement2(k, nums);
//            System.out.println(y++ + ".\tInput array: " + Arrays.toString(nums) + ", k = " + k);
//            System.out.println("\tResult: " + result);
//            System.out.println("-".repeat(100));
//        }

        int[] nums = {3, 6, 9, 10};
        int[] temp = {3, 6, 9, 10};
        System.out.println("Initial stream: " + Arrays.toString(nums));
        System.out.println("k: " + 3);

        KthLargestElementMain kLargest = new KthLargestElementMain(3, nums);
        int[] val = {4, 7, 10, 8, 15};
        List<Integer> tempList = new ArrayList<>();
        for (int v : temp) tempList.add(v);

        for (int i = 0; i < val.length; i++) {
            System.out.println("\tAdding a new number " + val[i] + " to the stream");
            tempList.add(val[i]);
            System.out.println("\tNumber stream: " + tempList);
            System.out.println("\tKth largest element in the stream: " + kLargest.add(val[i]));
            System.out.println("-".repeat(100));
        }
    }
}

