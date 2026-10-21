package org.educative.modules.module2.linklists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeTwoListsSolution {

    private static class ListNode {
        int val;
        ListNode next;

        // Constructor
        public ListNode(int val) {
            this.val = val;
            this.next = null;
        }
    }

    private static class LinkedList {
        ListNode head;

        // Default constructor
        public LinkedList() {
            head = null;
        }

        // Constructor to initialize from a list of values
        public LinkedList(List<Integer> values) {
            head = null;
            createLinkedList(values);
        }

        // Function to create a linked list from a list of values
        private void createLinkedList(List<Integer> values) {
            if (values.isEmpty()) {
                head = null;
                return;
            }

            head = new ListNode(values.get(0));
            ListNode current = head;
            for (int i = 1; i < values.size(); i++) {
                current.next = new ListNode(values.get(i));
                current = current.next;
            }
        }

    }

    private static class PrintList{
        // Function to display the linked list
        public static void display(ListNode head) {
            ListNode current = head;
            while (current != null) {
                System.out.print(current.val + " -> ");
                current = current.next;
            }
            System.out.println("None");
        }
    }

    private static ListNode buildLinkedList(int[] arr) {
        if (arr.length == 0) return null;
        ListNode head = new ListNode(arr[0]);
        ListNode curr = head;
        for (int i = 1; i < arr.length; i++) {
            curr.next = new ListNode(arr[i]);
            curr = curr.next;
        }
        return head;
    }

    private static List<Integer> toList(ListNode head) {
        List<Integer> result = new ArrayList<>();
        while (head != null) {
            result.add(head.val);
            head = head.next;
        }
        return result;
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // Create a dummy node to simplify edge cases
        ListNode dummy = new ListNode(0);
        // Tail pointer tracks the end of the merged list
        ListNode tail = dummy;

        // Traverse both lists, always picking the smaller current node
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                tail.next = list1;
                list1 = list1.next;
            } else {
                tail.next = list2;
                list2 = list2.next;
            }
            tail = tail.next;
        }

        // Attach remaining nodes from whichever list is non-empty
        tail.next = (list1 != null) ? list1 : list2;

        // Return head of merged list, skipping the dummy node
        return dummy.next;
    }

    public static void main(String[] args) {
        MergeTwoListsSolution sol = new MergeTwoListsSolution();

        int[][][] testCases = {
                {{2, 5, 7}, {1, 3, 6, 8}},
                {{1, 1, 1}, {1, 1, 1}},
                {{-10, -5, 0, 5}, {-7, -3, 2, 10}},
                {{100}, {-100}},
                {{0, 10, 20, 30, 40, 50}, {5, 15, 25, 35, 45}}
        };

        int y = 1;
        for (int[][] tc : testCases) {
            ListNode list1 = buildLinkedList(tc[0]);
            ListNode list2 = buildLinkedList(tc[1]);
            ListNode mergedHead = sol.mergeTwoLists(list1, list2);
            List<Integer> result = toList(mergedHead);
            System.out.println(y++ + ".\tlist1: " + Arrays.toString(tc[0]));
            System.out.println("\tlist2: " + Arrays.toString(tc[1]));
            System.out.println("\n\tResult: " + result);
            System.out.println("-".repeat(100));
        }
    }
}
