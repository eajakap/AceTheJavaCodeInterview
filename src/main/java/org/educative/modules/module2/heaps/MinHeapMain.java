package org.educative.modules.module2.heaps;

public class MinHeapMain {

    private static class MinHeap {
        private int capacity;
        private int[] data;
        private int size;

        public MinHeap(int capacity) {
            // Initialize an empty min heap with given capacity
            this.capacity = capacity;        // Maximum number of elements
            this.data = new int[capacity];   // Array to store elements
            this.size = 0;                   // Current number of elements
        }

        public MinHeap() {
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
            heapifyUp(size);
            size++;
            return true;
        }

        public int extractMin() {
            // Remove and return the minimum element
            if (isEmpty()) {
                System.out.println("Heap Underflow! Cannot extract from empty heap.");
                return -1;
            }
            int minValue = data[0]; // save the minimum value to return
            size--;
            if (size > 0) {
                data[0] = data[size];
                heapifyDown(0);
            }
            data[size] = 0;
            return minValue;
        }

        public boolean remove(int value) {
            if (isEmpty()) {
                return false;
            }

            int index = -1;
            for (int i = 0; i < size; i++) {
                if (data[i] == value) {
                    index = i;
                    break;
                }
            }

            if (index == -1) {
                return false;
            }

            size--;
            if (index == size) {
                data[size] = 0;
                return true;
            }

            data[index] = data[size];
            data[size] = 0;

            if (index > 0 && data[index] < data[parent(index)]) {
                heapifyUp(index);
            } else {
                heapifyDown(index);
            }
            return true;
        }

        private void heapifyUp(int index) {
            while (index > 0) {
                int parentIndex = parent(index);
                if (data[index] >= data[parentIndex]) {
                    break;
                }
                swap(index, parentIndex);
                index = parentIndex;
            }
        }

        private void heapifyDown(int index) {
            while (true) {
                int leftChildIndex = leftChild(index);
                int rightChildIndex = rightChild(index);
                int smallestIndex = index;

                if (leftChildIndex < size && data[leftChildIndex] < data[smallestIndex]) {
                    smallestIndex = leftChildIndex;
                }
                if (rightChildIndex < size && data[rightChildIndex] < data[smallestIndex]) {
                    smallestIndex = rightChildIndex;
                }

                if (smallestIndex == index) {
                    break;
                }

                swap(index, smallestIndex);
                index = smallestIndex;
            }
        }

        private int parent(int index) {
            return (index - 1) / 2;
        }

        private int leftChild(int index) {
            return 2 * index + 1;
        }

        private int rightChild(int index) {
            return 2 * index + 2;
        }

        private void swap(int i, int j) {
            int temp = data[i];
            data[i] = data[j];
            data[j] = temp;
        }

        public int peek() {
            // Return the minimum element without removing it
            if (isEmpty()) {
                System.out.println("Heap is empty! No element to peek.");
                return -1;
            }
            return data[0];
        }

    }

    private static class Main {
        public static void main(String[] args) {
            MinHeap heap = new MinHeap(10);
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
            System.out.println("1st extraction - minimum element removed: " + heap.extractMin());  // Output: 2
            System.out.println("2nd extraction - minimum element removed: " + heap.extractMin());  // Output: 5
            System.out.println("Heap size after extracting 2 minimum elements: " + heap.getSize());  // Output: 3
            System.out.println("Peek after extraction - new maximum element (heap unchanged): " + heap.peek()); // Output: 10

        }
    }

    public static void main(String[] args) {
        Main.main(args);
    }
}
