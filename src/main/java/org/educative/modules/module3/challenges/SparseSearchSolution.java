package org.educative.modules.module3.challenges;

/**
 * Demonstrates ways to search a sorted string array containing empty-string entries.
 */
public class SparseSearchSolution {

    /**
     * Searches every array position in order.
     */
    static class BruteForceSparseSearch {
        /**
         * Returns the index of the first matching string in a sparse array.
         *
         * @param array the array to search
         * @param target the non-null string to find
         * @return the index of the first match, or {@code -1} if the target is absent
         */
        public static int searchForString(String[] array, String target)
        {
            // A linear scan can skip no positions, so empty entries need no special handling.
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals(target)) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * Runs examples of the linear sparse-search approach.
         *
         * @param args command-line arguments (unused)
         */
        public static void main(String args[]) {
            String[] array = {
                    "",
                    "educative",
                    "",
                    "",
                    "",
                    "hello",
                    "",
                    "learning",
                    "world",
                    "",
                    "",
                    ""
            };
            String[] targetArray = {
                    "educative",
                    "learning"
            };
            System.out.println("Brute Force Approach:");
            for (int i = 0; i < 2; i++) {
                System.out.println(targetArray[i] + ": " + searchForString(array, targetArray[i]));
            }
        }
    }

    /**
     * Searches a sparse sorted array using a modified binary search.
     */
    static class ModifiedBinarySearchSparseSearch {
        /**
         * Searches an array whose non-empty strings are in ascending order.
         *
         * <p>When a midpoint is empty, the search finds a nearby non-empty entry
         * within the current bounds before comparing strings.</p>
         *
         * @param array the sparse array to search; non-empty entries must be sorted
         * @param target the non-null string to find
         * @return an index containing the target, or {@code -1} if it is absent
         */
        public static int searchForString(String[] array, String target)
        {
            int left = 0;
            int right = array.length - 1;

            while (left <= right) {
                int mid = left + (right - left) / 2;

                // Binary search needs a value to compare, so look outward from an empty midpoint.
                if (array[mid].isEmpty()) {
                    int leftMid = mid - 1;
                    int rightMid = mid + 1;

                    while (true) {
                        if (leftMid < left && rightMid > right) {
                            return -1;
                        } else if (rightMid <= right && !array[rightMid].isEmpty()) {
                            // Prefer the first non-empty value found to the right.
                            mid = rightMid;
                            break;
                        } else if (leftMid >= left && !array[leftMid].isEmpty()) {
                            mid = leftMid;
                            break;
                        }
                        rightMid++;
                        leftMid--;
                    }
                }

                // Compare the non-empty midpoint and discard the half that cannot contain the target.
                if (array[mid].equals(target)) {
                    return mid;
                } else if (array[mid].compareTo(target) < 0) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            return -1;
        }

        /**
         * Runs examples of the modified binary-search approach.
         *
         * @param args command-line arguments (unused)
         */
        public static void main(String args[]) {
            String[] array = {
                    "",
                    "educative",
                    "",
                    "",
                    "",
                    "hello",
                    "",
                    "learning",
                    "world",
                    "",
                    "",
                    ""
            };
            String[] targetArray = {
                    "educative",
                    "learning"
            };
            System.out.println("Modified Binary Search Approach:");
            for (int i = 0; i < 2; i++) {
                System.out.println(targetArray[i] + ": " + searchForString(array, targetArray[i]));
            }
        }
    }

    /**
     * Runs the examples for both sparse-search approaches.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        BruteForceSparseSearch.main(args);
        ModifiedBinarySearchSparseSearch.main(args);
    }
}
