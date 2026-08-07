package org.educative.module3.datastructures.linklists.single;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReverseLinkedListKGroup {
    private static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    private static class Solution {
        public ListNode reverseKGroup(ListNode head, int k) {
            // Check if there are at least k nodes remaining
            int count = 0;
            ListNode curr = head;
            while (curr != null && count < k) {
                curr = curr.next;
                count++;
            }

            // If fewer than k nodes remain, return head as-is
            if (count < k) {
                return head;
            }

            // Reverse k nodes in-place
            ListNode prev = null;
            curr = head;
            for (int i = 0; i < k; i++) {
                ListNode nextNode = curr.next;  // Save next pointer
                curr.next = prev;               // Reverse the link
                prev = curr;                    // Move prev forward
                curr = nextNode;                // Move curr forward
            }

            // head is now the tail of the reversed group; connect to next reversed group
            head.next = reverseKGroup(curr, k);

            // prev is the new head of this reversed group
            return prev;
        }

        // Helper: build linked list from array
        public static ListNode buildList(int[] arr) {
            if (arr == null || arr.length == 0) return null;
            ListNode head = new ListNode(arr[0]);
            ListNode curr = head;
            for (int i = 1; i < arr.length; i++) {
                curr.next = new ListNode(arr[i]);
                curr = curr.next;
            }
            return head;
        }

        // Helper: convert linked list to list for printing
        public static List<Integer> toList(ListNode head) {
            List<Integer> result = new ArrayList<>();
            ListNode curr = head;
            while (curr != null) {
                result.add(curr.val);
                curr = curr.next;
            }
            return result;
        }

        public static void main(String[] args) {
            Solution sol = new Solution();

            int[][][] testCases = {
                    {{1, 2, 3, 4, 5, 6},          {3}},
                    {{1, 2, 3, 4, 5, 6, 7, 8},    {4}},
                    {{10, 20, 30, 40, 50},         {1}},
                    {{5, 10, 15, 20, 25, 30, 35},  {2}},
                    {{100, 200, 300, 400},         {4}},
            };

            int i = 1;
            for (int[][] tc : testCases) {
                int[] arr = tc[0];
                int k = tc[1][0];

                ListNode head = buildList(arr);
                ListNode resultHead = sol.reverseKGroup(head, k);
                List<Integer> resultArr = toList(resultHead);

                System.out.println(i + ".\thead: " + Arrays.toString(arr));
                System.out.println("\tk: " + k);
                System.out.println("\n\tResult: " + resultArr);
                System.out.println(new String(new char[100]).replace('\0', '-'));
                i++;
            }
        }
    }

    public static void main(String[] args) {
        Solution.main(args);
    }
}
