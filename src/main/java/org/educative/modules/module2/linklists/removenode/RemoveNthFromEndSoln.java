package org.educative.modules.module2.linklists.removenode;

import java.util.Arrays;

public class RemoveNthFromEndSoln {

    private static final class ListNode {
        private final int value;
        private ListNode next;

        private ListNode(int value) {
            this.value = value;
        }
    }

    private static final class LinkedList {
        private final ListNode head;

        private LinkedList(ListNode head) {
            this.head = head;
        }

        private static LinkedList fromArray(int[] values) {
            if (values.length == 0) {
                return new LinkedList(null);
            }

            ListNode head = new ListNode(values[0]);
            ListNode current = head;
            for (int i = 1; i < values.length; i++) {
                current.next = new ListNode(values[i]);
                current = current.next;
            }
            return new LinkedList(head);
        }
    }

    private static final class Solution {
        private ListNode removeNthFromEnd(ListNode head, int n) {
            if (n <= 0) {
                throw new IllegalArgumentException("n must be positive");
            }

            ListNode dummy = new ListNode(0);
            dummy.next = head;

            ListNode fast = dummy;
            // advance fast pointer by n + 1 steps to maintain the gap
            for (int step = 0; step <= n; step++) {
                if (fast == null) {
                    throw new IllegalArgumentException("n cannot exceed the list length");
                }
                fast = fast.next;
            }

            // Move both pointers until fast reaches the end
            ListNode slow = dummy;
            while (fast != null) {
                fast = fast.next;
                slow = slow.next;
            }

            // Remove the nth node from the end
            // slow is now pointing to the node before the one we want to remove
            slow.next = slow.next.next;
            return dummy.next;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        int[][] values = {
                {10, 20, 30, 40, 50},
                {5, 10, 15},
                {7, 14, 21, 28},
                {0, 50, 100},
                {3, 6, 9, 12, 15, 18}
        };
        int[] positionsFromEnd = {1, 3, 2, 2, 4};

        for (int index = 0; index < values.length; index++) {
            ListNode updatedHead = solution.removeNthFromEnd(
                    LinkedList.fromArray(values[index]).head,
                    positionsFromEnd[index]
            );

            System.out.println((index + 1) + ".\tLinked List: " + Arrays.toString(values[index]));
            System.out.println("\tn: " + positionsFromEnd[index]);
            System.out.println("\n\tResult: " + toDisplayString(updatedHead));
            System.out.println("-".repeat(100));
        }
    }

    private static String toDisplayString(ListNode head) {
        StringBuilder builder = new StringBuilder();
        ListNode current = head;

        while (current != null) {
            builder.append(current.value).append(" -> ");
            current = current.next;
        }

        builder.append("None");
        return builder.toString();
    }
}
