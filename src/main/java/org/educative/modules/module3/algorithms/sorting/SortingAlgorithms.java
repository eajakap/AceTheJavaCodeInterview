package org.educative.modules.module3.algorithms.sorting;

/**
 * The SortingAlgorithms class provides implementations of various sorting algorithms, including Selection Sort,
 * Insertion Sort, Bubble Sort, Merge Sort, and Quick Sort. Each sorting algorithm is implemented as a static inner
 * class with its own methods for sorting an array of integers. The main method demonstrates the usage of these
 * sorting algorithms by testing them on a sample array.
 * <p>
 * Complexity Analysis:
 * 1. Selection Sort:
 *    - Time Complexity: O(n^2) in all cases (best, average, and worst) due to two nested loops.
 *    - Space Complexity: O(1) as it only requires a constant amount of additional space for variables.
 * 2. Insertion Sort:
 *    - Time Complexity: O(n^2) in the worst case, O(n) in the best case (already sorted), and O(n^2) on average.
 *    - Space Complexity: O(1) as it only requires a constant amount of additional space for variables.
 * 3. Bubble Sort:
 *    - Time Complexity: O(n^2) in the worst and average cases, O(n) in the best case (already sorted).
 *    - Space Complexity: O(1) as it only requires a constant amount of additional space for variables.
 * 4. Merge Sort:
 *    - Time Complexity: O(n log n) in all cases (best, average, and worst) due to the divide and conquer approach.
 *    - Space Complexity: O(n) as it requires additional space for temporary arrays used during merging.
 * 5. Quick Sort:
 *    - Time Complexity: O(n log n) on average, O(n^2) in the worst case (when the smallest or largest element is always chosen as the pivot).
 *    - Space Complexity: O(log n) due to the recursive stack space used during the sorting.
 * <p>
 * Time Complexities of Sorting Algorithms (Overview)
 * Explore the time complexities of major sorting algorithms including Insertion, Selection, Merge, Quick, and Bubble Sort.
 * Understand best, average, and worst case scenarios to improve your problem-solving skills for coding interviews.
 * <p>
 * Now that we’ve gone over how most famous and important sorting algorithms work, here’s a quick table that you can
 * refer to when you are solving coding problems!
 *
 * <pre>
 * |              | Insertion | Selection | Merge      | Quick      | Bubble |
 * |--------------|-----------|-----------|------------|------------|--------|
 * | Best case    | O(n)      | O(n^2)    | O(n log n) | O(n log n) | O(n)   |
 * | Average case | O(n^2)    | O(n^2)    | O(n log n) | O(n log n) | O(n^2) |
 * | Worst case   | O(n^2)    | O(n^2)    | O(n log n) | O(n^2)     | O(n^2) |
 * </pre>
 *
 * Usage:
 *   - To use the SortingAlgorithms class, simply call the main method, which will execute the test methods for each sorting algorithm.
 */
class SortingAlgorithms {
    /**
     * Helper class containing utility methods for sorting algorithms.
     * It provides methods to find the maximum and minimum values in an array,
     * swap elements in an array, and print the contents of an array.
     */
    static class Helper {
        /**
         * Finds the maximum value in an integer array.
         * @param arr the array to search
         * @param arrSize the size of the array
         * @return the maximum value found in the array
         */
        int findMax(int arr[], int arrSize) {
            int max = arr[0];
            for (int i = 1; i < arrSize; i++) {
                if (arr[i] > max) {
                    max = arr[i];
                }
            }
            return max;
        }
        /**
         * Finds the index of the maximum value in a subarray of an integer array.
         * @param arr the array to search
         * @param start the starting index of the subarray
         * @param end the ending index of the subarray
         * @return the index of the maximum value found in the subarray
         */
        int findMax(int[] arr, int start, int end) {
            if(end<=0 || start < 0)
                return 0;

            int maxInd = start;
            for(int i = start+1; i <= end; i++) {
                if(arr[maxInd] < arr[i])
                    maxInd = i;
            }
            return maxInd;
        }

        /**
         * Finds the minimum value in an integer array.
         * @param arr the array to search
         * @param arrSize the size of the array
         * @return the minimum value found in the array
         */
        int findMin(int arr[], int arrSize) {
            int min = arr[0];
            for (int i = 1; i < arrSize; i++) {
                if (arr[i] < min) {
                    min = arr[i];
                }
            }
            return min;
        }

        /**
         * Finds the index of the minimum value in a subarray of an integer array.
         * @param arr the array to search
         * @param start the starting index of the subarray
         * @param end the ending index of the subarray
         * @return the index of the minimum value found in the subarray
         */
        int findMin(int[] arr, int start, int end) {
            if(end<=0 || start < 0)
                return 0;

            int minInd = start;
            for(int i = start+1; i <= end; i++) {
                if(arr[minInd] > arr[i])
                    minInd = i;
            }
            return minInd;
        }
        /**
         * Swaps two elements in an integer array.
         * @param array the array containing the elements to swap
         * @param i the index of the first element
         * @param j the index of the second element
         */
         void swap(int[] array, int i, int j) {
            if (i == j) {
                return;
            }
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }

        /**
         * Prints the elements of an integer array.
         * @param arr the array to print
         * @param arrSize the size of the array
         */
        void printArray(int arr[], int arrSize) {
            for (int i = 0; i < arrSize; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Selection Sort Algorithm
     * Steps:
     * 1. Traverse through the array from the first element to the second last element.
     * 2. For each element, assume it is the minimum and store its index.
     * 3. Traverse the unsorted portion of the array to find the actual minimum element
     * 4. If a smaller element is found, update the index of the minimum element
     * 5. After finding the minimum element in the unsorted portion, swap it with the first element of the unsorted portion.
     * 6. Repeat the process for the next element until the entire array is sorted.
     * 7. The algorithm continues until the last element is reached, at which point the array is sorted.
     * 8. The time complexity of the selection sort algorithm is O(n^2) in all cases (best, average, and worst)
     *      because it always requires two nested loops to traverse the array.
     * 9. The space complexity is O(1) because it only requires a constant amount of additional space for variables
     *      used in the sorting process, regardless of the size of the input array.
     * 10. Selection sort is not a stable sorting algorithm, meaning that the relative order of equal elements may
     *      not be preserved after sorting. This is because the algorithm may swap elements that are equal,
     *      potentially changing their order in the sorted array.
     * 11. Selection sort is an in-place sorting algorithm, meaning that it does not require additional space
     *      proportional to the input size. It sorts the array by rearranging the elements
     * Time Complexity: O(n^2)
     * Space Complexity: O(1)
     */
    static class SelectionSort {
        /** Shared helper used for printing arrays. */
        static Helper obj = new Helper();

        /**
         * Sorts the first {@code arrSize} elements of the array in ascending order using selection sort.
         * The array is sorted in place.
         * @param arr the array to sort
         * @param arrSize the number of elements to sort
         */
        static void selectionSort(int[] arr, int arrSize) {
            int minIndex, temp;
            //Traverse through 0 to size of the array
            for (int i = 0; i < arrSize - 1; i++) {
                minIndex = i; // Assume the current index is the minimum
                //Find the index of the minimum element in the unsorted portion of the array
                for (int j = i + 1; j < arrSize; j++) {
                    if (arr[j] < arr[minIndex]) {
                        minIndex = j;
                    }
                }
                //Swap the found minimum element with the first element of the unsorted portion
                temp = arr[minIndex];
                arr[minIndex] = arr[i];
                arr[i] = temp;
            }
        }
        /**
         * Runs selection sort on a sample array and prints the array before and after sorting.
         */
        public static void testSelectionSort() {
            int arr[] = {5,4,1,0,5,95,4,-100,200,0};
            int arrSize = 10;
            System.out.print("Selection Sort: Initial Array: ");
            obj.printArray(arr, arrSize);
            SelectionSort.selectionSort(arr, arrSize);
            System.out.print("Selection Sort: Sorted Array: ");
            obj.printArray(arr, arrSize);
        }
    }

    /**
     * Insertion Sort Algorithm
     * Steps:
     * 1. Traverse through the array from the second element to the last element.
     * 2. For each element, store it in a variable (ele) and initialize a variable (j) to the index of the previous element.
     * 3. While j is greater than or equal to 0 and the element at index j is greater than ele,
     *      shift the element at index j to the right (arr[j + 1] = arr[j]) and decrement j.
     * 4. After finding the correct position for ele, insert it at index j + 1 (arr[j + 1] = ele).
     * 5. Repeat the process for the next element until the entire array is sorted.
     * 6. The time complexity of the insertion sort algorithm is O(n^2) in the worst case,
     *      which occurs when the input array is sorted in reverse order.
     *      In the best case, when the array is already sorted, the time complexity is O(n).
     *      The average case time complexity is also O(n^2).
     * 7. The space complexity is O(1) because it only requires a constant amount of additional space for variables
     *      used in the sorting process, regardless of the size of the input array.
     * 8. Insertion sort is a stable sorting algorithm, meaning that it preserves the relative order of equal elements
     *      in the sorted output. This is because it only shifts elements that are greater than ele,
     *      leaving equal elements in their original order.
     * 9. Insertion sort is an in-place sorting algorithm, meaning that it does not require additional space
     *      proportional to the input size. It sorts the array by rearranging the elements within the original array.
     * Time Complexity: O(n^2)
     * Space Complexity: O(1)
     */
    static class InsertionSort {
        /** Shared helper used for printing arrays. */
        static Helper obj = new Helper();

        /**
         * Sorts the first {@code arrSize} elements of the array in ascending order using insertion sort.
         * The array is sorted in place.
         * @param arr the array to sort
         * @param arrSize the number of elements to sort
         */
        static void insertionSort(int[] arr, int arrSize) {
            int ele, j;
            //Traverse through 1 to size of the array
            for (int i = 1; i < arrSize; i++) {
                ele = arr[i]; // Element to be inserted
                j = i - 1;

                //shifts elements back to create space for the element to be inserted
                while (j >= 0 && arr[j] > ele) {
                    arr[j + 1] = arr[j];
                    j = j - 1;
                }
                // Place the element in the gap left by the shifted elements
                arr[j + 1] = ele;
            }
        }

        /**
         * Runs insertion sort on a sample array and prints the array before and after sorting.
         */
        public static void testInsertionSort() {
            int arr[] = {5,4,1,0,5,95,4,-100,200,0};
            int arrSize = 10;
            System.out.print("Insertion Sort: Initial Array: ");
            obj.printArray(arr, arrSize);
            insertionSort(arr, arrSize);
            System.out.print("Insertion Sort: Sorted Array: ");
            obj.printArray(arr, arrSize);
        }
    }

    /**
     * Bubble Sort Algorithm
     * Steps:
     * 1. Traverse through the array from the first element to the second last element.
     * 2. For each element, compare it with the next element.
     * 3. If the current element is greater than the next element, swap them.
     * 4. Repeat the process for the next element until the entire array is sorted.
     * 5. The time complexity of the bubble sort algorithm is O(n^2) in the worst and average cases,
     *      which occurs when the input array is sorted in reverse order or when the elements are randomly ordered.
     *      In the best case, when the array is already sorted, the time complexity is O(n) because the algorithm
     *      only needs to make a single pass through the array to confirm that it is sorted.
     * 6. The space complexity is O(1) because it only requires a constant amount of additional space for variables
     *    used in the sorting process, regardless of the size of the input array.
     * 7. Bubble sort is a stable sorting algorithm, meaning that it preserves the relative order of equal elements
     *    in the sorted output. This is because it only swaps adjacent elements when they are out of order,
     *    leaving equal elements in their original order.
     * 8. Bubble sort is an in-place sorting algorithm, meaning that it does not require additional space proportional
     *    to the input size. It sorts the array by rearranging the elements within the original array.
     * Time Complexity: O(n^2)
     * Space Complexity: O(1)
     */
    static class BubbleSort {
        /** Shared helper used for swapping elements and printing arrays. */
        static Helper obj = new Helper();

        /**
         * Sorts the first {@code arrSize} elements of the array in ascending order using bubble sort.
         * Stops early if a full pass makes no swaps, which gives O(n) on an already sorted array.
         * The array is sorted in place.
         * @param arr the array to sort
         * @param arrSize the number of elements to sort
         */
        static void bubbleSort(int[] arr, int arrSize) {
            boolean swapped;
            // Traverse through all array elements
            for (int i = 0; i < arrSize - 1; i++) {
                swapped = false;
                // Last i elements are already sorted, so we can skip them
                for (int j = 0; j < arrSize - i - 1; j++) {
                    // Swap if the element found is greater than the next element
                    if (arr[j] > arr[j + 1]) {
                        obj.swap(arr, j, j + 1);
                        swapped = true;
                    }
                }
                // If no two elements were swapped in the inner loop, then the array is already sorted
                if (!swapped) {
                    break;
                }
            }
        }
        /**
         * Runs bubble sort on a sample array and prints the array before and after sorting.
         */
        public static void testBubbleSort() {
            int arr[] = {5,4,1,0,5,95,4,-100,200,0};
            int arrSize = 10;
            System.out.print("Bubble Sort: Initial Array: ");
            obj.printArray(arr, arrSize);
            bubbleSort(arr, arrSize);
            System.out.print("Bubble Sort: Sorted Array: ");
            obj.printArray(arr, arrSize);
        }

    }

    /**
     * Merge Sort Algorithm
     * Merge sort is a recursive divide and conquer algorithm that essentially divides a given array into two halves,
     * sorts those halves, and merges them together in order. The base case merges two arrays of size 1 so that,
     * eventually, the single elements are merged in order; the merge part is where most of the heavy lifting happens.
     * Steps:
     * 1. If the array has one or zero elements, it is already sorted, so return.
     * 2. Divide the array into two halves by finding the middle index.
     * 3. Recursively call mergeSort on the left half of the array.
     * 4. Recursively call mergeSort on the right half of the array.
     * 5. Merge the two sorted halves back together using the merge function.
     * 6. The merge function compares elements from both halves and places them in sorted order into a temporary array.
     * 7. Copy the sorted elements from the temporary array back into the original array.
     * 8. Repeat this process until the entire array is sorted.
     * 9. The time complexity of the merge sort algorithm is O(n log n) in all cases (best, average, and worst)
     *      because it divides the array into halves (log n) and merges them back together (n).
     * 10. The space complexity is O(n) because it requires additional space for temporary arrays used during merging,
     *      which is proportional to the size of the input array.
     * 11. Merge sort is a stable sorting algorithm, meaning that it preserves the relative order of equal elements
     *      in the sorted output. This is because it merges elements in a way that maintains their original order.
     * 12. Merge sort is not an in-place sorting algorithm, meaning that it requires additional space proportional
     *      to the input size for temporary arrays used during merging.
     * Time Complexity: O(n log n)
     * Space Complexity: O(n)
     */
    static class MergeSort {
        /** Shared helper used for printing arrays. */
        static Helper obj = new Helper();

        /**
         * Merges two adjacent sorted subarrays, {@code arr[leftHalf..middle]} and {@code arr[middle+1..rightHalf]},
         * into a single sorted subarray {@code arr[leftHalf..rightHalf]}.
         * Equal elements are taken from the left half first, which keeps the sort stable.
         * @param arr the array containing both subarrays
         * @param leftHalf the starting index of the left subarray
         * @param middle the ending index of the left subarray
         * @param rightHalf the ending index of the right subarray
         */
        static void merge(int arr[], int leftHalf, int middle, int rightHalf) {
            // Find sizes of two subarrays to be merged
            int leftHalfSize = middle - leftHalf + 1;
            int rightHalfSize = rightHalf - middle;

            // Create temporary arrays
            int L[] = new int[leftHalfSize];
            int R[] = new int[rightHalfSize];

            // Copy data to temporary arrays
            for (int i = 0; i < leftHalfSize; ++i)
                L[i] = arr[leftHalf + i];
            for (int j = 0; j < rightHalfSize; ++j)
                R[j] = arr[middle + 1 + j];

            // Merge the temporary arrays

            // Initial indexes of first and second subarrays
            int i = 0, j = 0;

            // Initial index of merged subarray array
            int k = leftHalf;
            // Repeatedly copy the smaller front element of L[] or R[] back into arr[]
            while (i < leftHalfSize && j < rightHalfSize) {
                if (L[i] <= R[j]) {
                    arr[k] = L[i];
                    i++;
                } else {
                    arr[k] = R[j];
                    j++;
                }
                k++;
            }

            // Copy remaining elements of L[] if any
            while (i < leftHalfSize) {
                arr[k] = L[i];
                i++;
                k++;
            }

            // Copy remaining elements of R[] if any
            while (j < rightHalfSize) {
                arr[k] = R[j];
                j++;
                k++;
            }
        }

        /**
         * Recursively sorts the subarray {@code arr[l..r]} in ascending order using merge sort.
         * @param arr the array to sort
         * @param l the starting index of the subarray (inclusive)
         * @param r the ending index of the subarray (inclusive)
         */
        static void mergeSort(int arr[], int l, int r) {
            // Base case: a subarray of zero or one element is already sorted
            if (l < r) {
                // Find the middle point to divide the array into two halves
                int m = (l + r) / 2;
                // Sort the left half
                mergeSort(arr, l, m);
                // Sort the right half
                mergeSort(arr, m + 1, r);
                // Merge the two sorted halves
                merge(arr, l, m, r);
            }
        }

        /**
         * Runs merge sort on a sample array and prints the array before and after sorting.
         */
        static void testMergeSort() {
            int arr[] = {5,4,1,0,5,95,4,-100,200,0};
            int arrSize = 10;
            System.out.print("Merge Sort: Initial Array: ");
            obj.printArray(arr, arrSize);
            mergeSort(arr, 0, arrSize - 1);
            System.out.print("Merge Sort: Sorted Array: ");
            obj.printArray(arr, arrSize);
        }
    }

    /**
     * Quick Sort Algorithm
     * Quick sort is a recursive divide and conquer algorithm that selects a pivot element from the array
     * and partitions the other elements into two subarrays according to whether they are less than or
     * greater than the pivot. The subarrays are then sorted recursively.
     * Steps:
     * 1. If the array has one or zero elements, it is already sorted, so return.
     * 2. Select a pivot element from the array (commonly the last element).
     * 3. Partition the array into two subarrays: elements less than or equal to the pivot and elements greater than the pivot.
     * 4. Recursively call quickSort on the left subarray (elements less than or equal to the pivot).
     * 5. Recursively call quickSort on the right subarray (elements greater than the pivot).
     * Time Complexity: O(n log n) on average, O(n^2) in the worst case (when the smallest or largest element is always chosen as the pivot).
     * Space Complexity: O(log n) due to the recursive stack space used during the sorting
     */
    static class QuickSort {
        /** Shared helper used for swapping elements and printing arrays. */
        static Helper obj = new Helper();

        /**
         * Recursively sorts the subarray {@code arr[low..high]} in ascending order using quick sort.
         * @param arr the array to sort
         * @param low the starting index of the subarray (inclusive)
         * @param high the ending index of the subarray (inclusive)
         */
        static void quickSort(int arr[], int low, int high) {
            // Base case: a subarray of zero or one element is already sorted
            if (low < high) {
                // Place the pivot at its final position and get that index
                int pi = partition(arr, low, high);
                // Sort the elements before and after the pivot
                quickSort(arr, low, pi - 1);
                quickSort(arr, pi + 1, high);
            }
        }

        /**
         * Partitions {@code arr[low..high]} around the last element as the pivot (Lomuto partition scheme).
         * After partitioning, elements less than or equal to the pivot are on its left
         * and elements greater than the pivot are on its right.
         * @param arr the array to partition
         * @param low the starting index of the subarray (inclusive)
         * @param high the ending index of the subarray (inclusive), which holds the pivot
         * @return the final index of the pivot
         */
        static int partition(int arr[], int low, int high) {
            int pivot = arr[high];
            // i marks the end of the region holding elements <= pivot
            int i = (low - 1);
            for (int j = low; j < high; j++) {
                // Grow the "<= pivot" region and move arr[j] into it
                if (arr[j] <= pivot) {
                    i++;
                    obj.swap(arr, i, j);
                }
            }
            // Put the pivot right after the "<= pivot" region
            obj.swap(arr, i + 1, high);
            return i + 1;
        }

        /**
         * Runs quick sort on a sample array and prints the array before and after sorting.
         */
        static void testQuickSort() {
            int arr[] = {5,4,1,0,5,95,4,-100,200,0};
            int arrSize = 10;
            System.out.print("Quick Sort: Initial Array: ");
            obj.printArray(arr, arrSize);
            quickSort(arr, 0, arrSize - 1);
            System.out.print("Quick Sort: Sorted Array: ");
            obj.printArray(arr, arrSize);
        }
    }
    /**
     * Main method to test the sorting algorithms.
     * It calls the test methods for Selection Sort, Insertion Sort, Bubble Sort, Merge Sort, and Quick Sort,
     * which demonstrate the sorting of a sample array using each algorithm.
     * @param args command-line arguments (not used)
     */
    public static void main(String args[]) {
        SelectionSort.testSelectionSort();
        InsertionSort.testInsertionSort();
        BubbleSort.testBubbleSort();
        MergeSort.testMergeSort();
        QuickSort.testQuickSort();
    }

}