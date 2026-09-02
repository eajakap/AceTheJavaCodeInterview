package org.educative.modules.module2.linklists.circular;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortedCircularLinkedList {
    private static final class ListNode {
        int val;
        ListNode next;
        public ListNode(int val) {
            this.val = val;
            this.next = null;
        }
    }

    private static final class CircularLinkedList {
        public ListNode createCircularLinkedList(int[] values) {
            if (values == null || values.length == 0) {
                return null;
            }

            ListNode head = new ListNode(values[0]);
            ListNode curr = head;
            for (int i = 1; i < values.length; i++) {
                curr.next = new ListNode(values[i]);
                curr = curr.next;
            }
            curr.next = head; // Make it circular
            return head;
        }

        public static List<Integer> toList(ListNode head) {
            List<Integer> result = new ArrayList<>();
            if (head == null) {
                return result;
            }

            ListNode curr = head;
            do {
                result.add(curr.val);
                curr = curr.next;
            } while (curr != head);

            return result;
        }
    }

    private static final class Solution {
        public ListNode insert(ListNode head, int insertVal) {
            // Create the new node to insert
            ListNode newNode = new ListNode(insertVal);

            // Case 1: Empty list - create a single-node circular list
            if (head == null) {
                head = newNode;
                newNode.next = newNode;
                return head;
            }

            // Case 2: Single node list
            if (head.next == head) {
                head.next = newNode;
                newNode.next = head;
                return head;
            }

            // Case 3: Traverse the circular list to find the correct insertion point
            ListNode curr = head;
            while (true) {
                // Condition A: insertVal fits between curr and curr.next in sorted order
                if (curr.val <= insertVal && insertVal <= curr.next.val) {
                    // valid insertion point found
                    break;
                }

                // Condition B: We are at the wrap-around point (max -> min transition)
                // and insertVal is either >= max or <= min
                if (curr.val > curr.next.val) {
                    if (insertVal >= curr.val || insertVal <= curr.next.val) {
                        // valid insertion point found
                        break;
                    }
                }

                // Move to the next node
                curr = curr.next;

                // Condition C: We've traversed the entire list (all values are the same)
                if (curr == head) {
                    break;
                }
            }

            // Insert the new node after curr
            newNode.next = curr.next;
            curr.next = newNode;

            return head;
        }

        public static void main(String[] args) {
            Solution sol = new Solution();

            int[][] arrays = {
                    {1, 3, 5},
                    {1, 3, 5},
                    {1, 3, 5},
                    {2, 2, 2},
                    {5, 6, 1, 2, 3}
            };

            int[] insertVals = {4, 6, 0, 2, -1};

            int y = 1;
            for (int t = 0; t < arrays.length; t++) {
                CircularLinkedList cll = new CircularLinkedList();
                ListNode head = cll.createCircularLinkedList(arrays[t]);
                ListNode resultHead = sol.insert(head, insertVals[t]);
                List<Integer> resultList = CircularLinkedList.toList(resultHead);

                System.out.println(y++ + ".\tList: " + Arrays.toString(arrays[t]));
                System.out.println("\tinsertVal: " + insertVals[t]);
                System.out.println("\n\tResult: " + resultList);
                System.out.println("-".repeat(100));
            }
        }
    }

    public static void main(String[] args) {
        Solution.main(args);
    }
}
