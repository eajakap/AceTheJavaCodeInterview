package org.educative.modules.module3.algorithms.searching;

/**
 * The SearchingAlgorithms class provides implementations of two classic searching algorithms: Linear Search and
 * Binary Search. Each searching algorithm is implemented as a static inner class with its own search method and a
 * main method that demonstrates the search on a sample array.
 * <p>
 * Complexity Analysis:
 * 1. Linear Search:
 *    - Time Complexity: O(1) in the best case (key is the first element), O(n) in the average and worst cases.
 *    - Space Complexity: O(1) as it only requires a constant amount of additional space for variables.
 *    - Works on any array, sorted or unsorted.
 * 2. Binary Search:
 *    - Time Complexity: O(1) in the best case (key is the middle element), O(log n) in the average and worst cases.
 *    - Space Complexity: O(1) for this iterative version.
 *    - Requires the array to be sorted in ascending order.
 *
 * <pre>
 * |              | Linear | Binary   |
 * |--------------|--------|----------|
 * | Best case    | O(1)   | O(1)     |
 * | Average case | O(n)   | O(log n) |
 * | Worst case   | O(n)   | O(log n) |
 * </pre>
 *
 * Usage:
 *   - Run the main method of LinearSearch or BinarySearch to see each algorithm search a sample array.
 */
public class SearchingAlgorithms {

    /**
     * Helper class containing utility methods for searching algorithms.
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
     * Linear Search Algorithm
     * Linear search checks each element of the array one by one, from the first to the last,
     * until it finds the key or reaches the end of the array.
     * Steps:
     * 1. If the array is empty, return -1.
     * 2. Traverse the array from the first element to the last element.
     * 3. Compare each element with the key.
     * 4. If an element matches the key, return its index.
     * 5. If the end of the array is reached without a match, return -1.
     * 6. The time complexity is O(n) in the worst case, which occurs when the key is the last element
     *      or is not present in the array. In the best case, when the key is the first element, it is O(1).
     *      The average case is O(n) because, on average, half of the array is checked.
     * 7. The space complexity is O(1) because it only requires a constant amount of additional space for variables,
     *      regardless of the size of the input array.
     * 8. Linear search does not require the array to be sorted, so it works on any array.
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    static class LinearSearch {
        /**
         * Searches the first {@code arrSize} elements of the array for the key using linear search.
         * If the key appears more than once, the index of its first occurrence is returned.
         * @param s the key to search for
         * @param arr the array to search (does not need to be sorted)
         * @param arrSize the number of elements to search
         * @return the index of the key, or -1 if it is not found
         */
        public static int linearSearch(int s, int[] arr, int arrSize) {
            if (arrSize <= 0) // Sanity check
                return -1;

            // Compare every element with the key, from left to right
            for (int i = 0; i < arrSize; i++)
                if (arr[i] == s)
                    return i; // If found return index
            return -1; // Return -1 otherwise
        }

        /**
         * Runs linear search for a sample key on an unsorted sample array and prints the result.
         * @param args command-line arguments (not used)
         */
        public static void main(String args[]) {
            int arr[] = {5,4,1,410,5,95,4,-100,20,0};
            int arrSize = 10;
            int key = 0;
            Helper obj = new Helper();
            System.out.println("Given Array");
            obj.printArray(arr, arrSize);

            int index = linearSearch(key, arr, arrSize);
            // An index of -1 means the key is not in the array
            if (index != -1)
                System.out.println("Your key \"" + key + "\" is found at index \"" + index + "\"");
            else
                System.out.println(key + " not found in the array: ");

        }
    }

    /**
     * Binary Search Algorithm
     * Binary search finds a key in a sorted array by repeatedly halving the search range. It compares the key with
     * the middle element and discards the half that cannot contain the key.
     * Steps:
     * 1. If the array is empty, return -1.
     * 2. Set start to the first index and end to the last index.
     * 3. While start is less than or equal to end, find the middle index.
     * 4. If the middle element is less than the key, the key can only be in the right half, so set start = mid + 1.
     * 5. If the middle element is greater than the key, the key can only be in the left half, so set end = mid - 1.
     * 6. Otherwise, the middle element equals the key, so return mid.
     * 7. If the range becomes empty (start > end), the key is not in the array, so return -1.
     * 8. The time complexity is O(log n) in the average and worst cases because the search range is halved
     *      on every iteration. In the best case, when the key is the middle element, it is O(1).
     * 9. The space complexity is O(1) because this iterative version only uses a few variables.
     *      A recursive version would use O(log n) stack space.
     * 10. Binary search only works if the array is sorted in ascending order.
     * Time Complexity: O(log n)
     * Space Complexity: O(1)
     */
    static class BinarySearch {
        /**
         * Searches the first {@code arrSize} elements of a sorted array for the key using binary search.
         * If the key appears more than once, the index of any one of its occurrences may be returned.
         * @param search the key to search for
         * @param arr the array to search, which must be sorted in ascending order
         * @param arrSize the number of elements to search
         * @return the index of the key, or -1 if it is not found
         */
        static int binarySearch(int search, int arr[], int arrSize) {
            if (arrSize <= 0) // Sanity check
                return -1;

            // Single element: just compare it with the key
            if (arrSize == 1) {
                if (arr[0] == search)
                    return 0;
                else
                    return -1;
            }

            // The key, if present, is always within arr[start..end]
            int start = 0;
            int end = arrSize - 1;
            int mid;

            while (start <= end) {
                mid = (end + start) / 2;
                if (arr[mid] < search)
                    start = mid + 1; // Key is in the right half
                else if (arr[mid] > search)
                    end = mid - 1; // Key is in the left half
                else
                    return mid; // Key found
            }
            return -1; // Search range is empty, so the key is not present
        }

        /**
         * Runs binary search for a sample key on a sorted sample array and prints the result.
         * @param args command-line arguments (not used)
         */
        public static void main(String args[]) {
            int arr[] = {-100,0,0,1,4,4,5,5,95,200};
            int arrSize = 10;
            int key = 1;
            int index = binarySearch(key, arr, arrSize);
            Helper obj = new Helper();
            obj.printArray(arr, arrSize);
            // An index of -1 means the key is not in the array
            if (index != -1)
                System.out.println("Your Key \"" + key + "\" is found at index \"" + index + "\"");
            else
                System.out.println("Your Key \"" + key + "\" not found in the array: ");
        }
    }
}
