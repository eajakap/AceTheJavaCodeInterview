package org.educative.modules.module2.heaps;

public class MaxHeapMain {

    private static class MaxHeap {
        private int capacity;
        private int[] data;
        private int size;

        public MaxHeap(int capacity) {
            // Initialize an empty min heap with given capacity
            this.capacity = capacity;        // Maximum number of elements
            this.data = new int[capacity];   // Array to store elements
            this.size = 0;                   // Current number of elements
        }

        public MaxHeap() {
            this(10);
        }

        public int getSize() {
            // Return the number of elements in the heap
            return size;
        }

        public boolean isEmpty() {
            // Check if the heap is empty
            return size == 0;
        }
        public boolean isFull() {
            // Check if the heap is full
            return size == capacity;
        }

        public boolean insert(int value) {
            // Add an element to the heap
            if (isFull()) {
                System.out.println("Heap Overflow! Cannot insert element.");
                return false;
            }
            data[size] = value;
            size++;
            int index = size - 1;
            while (index > 0) {
                int parent = (index - 1) / 2;
                if (data[index] > data[parent]) {
                    int temp = data[index];
                    data[index] = data[parent];
                    data[parent] = temp;
                    index = parent;
                } else {
                    break;
                }
            }
            return true;
        }

        public int extractMax() {
            // Remove and return the maximum element
            if (isEmpty()) {
                System.out.println("Heap Underflow! Cannot extract from empty heap.");
                return -1;
            }
            int maxValue = data[0]; // save the maximum value to return
            size--;
            data[0] = data[size]; // swap the last element to the root
            int index = 0;
            while (true) {
                int leftChildIndex = 2 * index + 1; // left child index
                int rightChildIndex = 2 * index + 2; // right child index
                int largestIndex = index;
                // compare the values of the current node and its children to find the largest
                if (leftChildIndex < size && data[leftChildIndex] > data[largestIndex]) {
                    largestIndex = leftChildIndex;
                }
                if (rightChildIndex < size && data[rightChildIndex] > data[largestIndex]) {
                    largestIndex = rightChildIndex;
                }
                // if the largest is not the current node, swap and continue
                if (largestIndex != index) {
                    int temp = data[index];
                    data[index] = data[largestIndex];
                    data[largestIndex] = temp;
                    index = largestIndex;
                } else {
                    break;
                }
            }
            return maxValue;
        }

        public int peek() {
            // Return the maximum element without removing it
            if (isEmpty()) {
                System.out.println("Heap is empty! No element to peek.");
                return -1;
            }
            return data[0];
        }

    }

    private static class Main {
        public static void main(String[] args) {
            MaxHeap heap = new MaxHeap(10);
            System.out.println("Is heap empty? " + heap.isEmpty());  // Output: true

            // Simulate a full heap with elements: [2, 5, 8, 10, 14]
            heap.size = 5;
            System.out.println("Is heap full? " + heap.isFull());  // Output: true

            System.out.println("Simulating extracting one element from the heap");
            heap.size = 4;
            System.out.println("Is heap full? " + heap.isFull());  // Output: false

            heap.size = 0;
            heap.insert(14);
            heap.insert(10);
            heap.insert(8);
            heap.insert(5);
            heap.insert(2);

            System.out.println("All elements have been inserted. Size of the heap: " + heap.getSize());  // Output: 5
            System.out.println("1st extraction - maximum element removed: " + heap.extractMax());  // Output: 14
            System.out.println("2nd extraction - maximum element removed: " + heap.extractMax());  // Output: 10
            System.out.println("Heap size after extracting 2 maximum elements: " + heap.getSize());  // Output: 3
            System.out.println("Peek after extraction - new maximum element (heap unchanged): " + heap.peek()); // Output: 10

        }
    }

    public static void main(String[] args) {
        Main.main(args);
    }
}
