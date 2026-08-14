package org.educative.modules.module2.heaps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class MedianOfStream {
    private PriorityQueue<Integer> maxHeapForSmallNum;
    private PriorityQueue<Integer> minHeapForLargeNum;

    public MedianOfStream() {
        maxHeapForSmallNum = new PriorityQueue<>(Collections.reverseOrder());
        minHeapForLargeNum = new PriorityQueue<>();
    }

    public void insertNum(int num) {
        if (maxHeapForSmallNum.isEmpty() || maxHeapForSmallNum.peek() >= num) {
            maxHeapForSmallNum.add(num);
        } else {
            minHeapForLargeNum.add(num);
        }

        if (maxHeapForSmallNum.size() > minHeapForLargeNum.size() + 1) {
            minHeapForLargeNum.add(maxHeapForSmallNum.poll());
        } else if (maxHeapForSmallNum.size() < minHeapForLargeNum.size()) {
            maxHeapForSmallNum.add(minHeapForLargeNum.poll());
        }
    }

    public double findMedian() {
        if (maxHeapForSmallNum.size() == minHeapForLargeNum.size()) {
            // we have even number of elements, take the average of middle two elements
            // we divide both numbers by 2.0 to ensure we add two floating point numbers
            return maxHeapForSmallNum.peek() / 2.0 + minHeapForLargeNum.peek() / 2.0;
        }
        // because max-heap will have one more element than the min-heap
        return maxHeapForSmallNum.peek() / 1.0;
    }

    public static void main(String[] args) {
        MedianOfStream medianNum = new MedianOfStream();
        int[] nums = {35, 22, 30, 25, 1};
        List<Integer> numList = new ArrayList<>();
        int x = 1;

        for (int i : nums) {
            numList.add(i);
            System.out.println(x + ".\tData stream: " + numList);
            medianNum.insertNum(i);
            System.out.println("\tThe median for the given numbers is: " + medianNum.findMedian());
            System.out.println("-".repeat(100) + "\n");
            x++;
        }
    }
}