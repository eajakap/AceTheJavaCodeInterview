package org.educative.modules.module2.heaps;

import java.util.*;

public class TopKFrequentMain {

    private PriorityQueue<Integer> topKFrequentHeap;
    private int k;
    private Map<Integer, Integer> frequencyMap;

    public TopKFrequentMain() {
        this.topKFrequentHeap = null;
        this.frequencyMap = null;
        this.k = 0;
    }

//    public TopKFrequentMain(int k, int[] nums) {
//        if (nums == null || nums.length == 0) return;
//        // min heap of size k to store the top k largest elements
//        this.frequencyMap = new HashMap<>();
//        this.topKFrequentHeap = new PriorityQueue<>(Comparator.comparingInt(a -> frequencyMap.get(a)));
//        this.k = k;
//
//        for (int element : nums) {
//            add(element);
//            frequencyMap.put(element, frequencyMap.getOrDefault(element, 0) + 1);
//        }
//    }
//
//    public int add(int val) {
//        frequencyMap.put(val, frequencyMap.getOrDefault(val, 0) + 1);
//
//        if (topKFrequentHeap.size() < k) {
//            topKFrequentHeap.add(val);
//        } else if (frequencyMap.get(val) > frequencyMap.get(topKFrequentHeap.peek())) {
//            topKFrequentHeap.poll();
//            topKFrequentHeap.add(val);
//        }
//
//        return topKFrequentHeap.peek();
//    }

    public int[] topKFrequent(int[] nums, int k) {
        // Build frequency map
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        // Min heap ordered by frequency
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            int num = entry.getKey();
            int freq = entry.getValue();
            minHeap.add(new int[]{freq, num});
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        int[] result = new int[k];
        int i = 0;
        for (int[] pair : minHeap) {
            result[i++] = pair[1]; // value is the number, frequency is pair[0]
        }
        return result;
    }

    public static void main(String[] args) {
        TopKFrequentMain topKFrequentMain = new TopKFrequentMain();
        int[][][] testCases = {
                {{4, 4, 4, 5, 5, 6, 6, 6, 6}, {2}},
                {{3, 3, 3, 1, 1, 2}, {1}},
                {{7, 7, 8, 8, 9, 9, 10}, {3}},
                {{-1, -1, -1, 2, 2, 3, 3, 3, 3}, {2}},
                {{100, 200, 100, 300, 200, 100, 300, 300, 200, 400}, {3}}
        };

        int y = 1;
        for (int[][] tc : testCases) {
            int[] nums = tc[0];
            int k = tc[1][0];
            int[] result = topKFrequentMain.topKFrequent(nums, k);
            System.out.println(y++ + ".\tInput array: " + Arrays.toString(nums));
            System.out.println("\tTarget: " + k);
            System.out.println("\tResult: " + Arrays.toString(result));
            System.out.println("-".repeat(100));
        }
    }
}

