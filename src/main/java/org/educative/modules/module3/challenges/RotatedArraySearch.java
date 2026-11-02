package org.educative.modules.module3.challenges;

/**
 * Demonstrates linear and modified binary searches on rotated arrays.
 */
public class RotatedArraySearch {

    /**
     * Searches a rotated array by checking its elements sequentially.
     */
    class BruteForceSearchArray {
        /**
         * Scans the array from index {@code 0} up to, but not including, {@code right}.
         *
         * @param arr the array to search
         * @param left unused lower-bound parameter
         * @param right the exclusive upper bound of the scan
         * @param n the value to find
         * @return the index of the first matching value in the scanned range, or {@code -1} if absent
         */
        static int searchRotatedArray(int arr[], int left, int right, int n) {
            // An empty scan range cannot contain the target.
            if (right <= 0)
                return -1;

            for (int i = 0; i < right; i++)
                if (arr[i] == n)
                    return i;

            return -1;
        }

        /**
         * Runs the linear-search example.
         *
         * @param args command-line arguments (unused)
         */
        public static void main(String args[]) {
            int arr[] = {40,100,-100,40,0,24,40};
            System.out.println(searchRotatedArray(arr, 0, 6, -100));
        }
    }

    /**
     * Searches a rotated array with a recursive binary-search strategy.
     */
    class ModifiedBinarySearchArray
    {
        /**
         * Searches the inclusive range from {@code left} to {@code right}.
         *
         * <p>When duplicate values make it impossible to determine which half is sorted,
         * the method may need to search both halves.</p>
         *
         * @param arr the rotated array to search
         * @param left the inclusive lower bound of the current search range
         * @param right the inclusive upper bound of the current search range
         * @param n the value to find
         * @return the index of a matching value, or {@code -1} if it is absent
         */
        static int searchRotatedArray(int arr[], int left, int right, int n) {
            int mid = (left + right) / 2;
            //int mid = left + (right - left) / 2;

            if (n == arr[mid])
                return mid;

            if (right < left)
                return -1;

            // The left half is sorted, so compare the target with its bounds.
            if (arr[left] < arr[mid]) {

                if (n >= arr[left] && n < arr[mid])
                    return searchRotatedArray(arr, left, mid - 1, n);
                else
                    return searchRotatedArray(arr, mid + 1, right, n);

            // Otherwise, if the right half is sorted, use its bounds.
            } else if (arr[mid] < arr[right]) {

                if (n > arr[mid] && n <= arr[right])
                    return searchRotatedArray(arr, mid + 1, right, n);
                else
                    return searchRotatedArray(arr, left, mid - 1, n);

            // Equal values obscure the rotation boundary; narrow or search both halves.
            } else if (arr[left] == arr[mid]) {

                if (arr[mid] != arr[right])
                    return searchRotatedArray(arr, mid + 1, right, n);
                else {
                    int result = searchRotatedArray(arr, left, mid - 1, n);
                    if (result == -1) {
                        return searchRotatedArray(arr, mid + 1, right, n);
                    } else
                        return result;
                }
            }
            return -1;
        }

        /**
         * Runs the modified binary-search example.
         *
         * @param args command-line arguments (unused)
         */
        public static void main(String args[])
        {
            int arr[] = {40,100,-100,40,0,24,40};
            System.out.println( "The index is: " + searchRotatedArray(arr, 0, 6, -100));
        }
    }

    /**
     * Runs the modified binary-search example using a sample rotated array.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        int arr[] = { 4, 5, 6, 7, 0, 1, 2 };
        int n = 0;
        int index = ModifiedBinarySearchArray.searchRotatedArray(arr, 0, arr.length - 1, n);
        if (index != -1) {
            System.out.println("Element found at index: " + index);
        } else {
            System.out.println("Element not found in the array.");
        }
    }

}
