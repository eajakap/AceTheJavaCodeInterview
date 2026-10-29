package org.educative.modules.module2.heaps;

import java.util.*;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

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

    public static List<Integer> topKFrequentV2(int[] arr, int k) {
        Map<Integer, Integer> numFrequencyMap = new HashMap<>();
        for (int n : arr) {
            numFrequencyMap.put(n, numFrequencyMap.getOrDefault(n, 0) + 1);
        }

        PriorityQueue<Map.Entry<Integer, Integer>> topKElements = new PriorityQueue<>(
                (e1, e2) -> e1.getValue() - e2.getValue()
        );

        for (Map.Entry<Integer, Integer> entry : numFrequencyMap.entrySet()) {
            topKElements.add(entry);
            if (topKElements.size() > k) {
                topKElements.poll();
            }
        }

        List<Integer> topNumbers = new ArrayList<>(k);
        while (!topKElements.isEmpty()) {
            topNumbers.add(topKElements.poll().getKey());
        }
        Collections.reverse(topNumbers);
        return topNumbers;
    }

    public static List<String> topK(List<String> xs, int k) {
        Map<String, Long> c = xs.stream().collect(groupingBy(s -> s, counting()));
        PriorityQueue<Map.Entry<String, Long>> h = new PriorityQueue<>(Map.Entry.comparingByValue());
        for (var e : c.entrySet()) { h.offer(e); if (h.size() > k) h.poll(); }
        List<String> out = new ArrayList<>(); while (!h.isEmpty()) out.add(h.poll().getKey());
        Collections.reverse(out); return out;
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
            System.out.println(y++ + ".\tInput array: " + Arrays.toString(nums));
            System.out.println("\tTarget: " + k);
            int[] result = topKFrequentMain.topKFrequent(nums, k);
            System.out.println("\tResult\t: " + Arrays.toString(result));
            List<Integer> result2 = topKFrequentMain.topKFrequentV2(nums, k);
            System.out.println("\tResult2\t: " + result2);
            List<String> inputTickers = List.of("IBM", "AAPL", "IBM", "MSFT", "IBM", "AAPL");
            System.out.println(y++ + ".\tInput array: " + inputTickers);
            List<String> result3 = topKFrequentMain.topK(inputTickers, k);
            System.out.println("\tResult3\t: " + result3);
            System.out.println("-".repeat(100));
        }
    }
}

