package org.educative.module3.datastructures.linklists.mergelists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class List1Node {
    int val;
    List1Node next;

    // Constructor
    public List1Node(int val) {
        this.val = val;
        this.next = null;
    }
}

class Linked1List {
    List1Node head;

    // Default constructor
    public Linked1List() {
        head = null;
    }

    // Constructor to initialize from a list of values
    public Linked1List(List<Integer> values) {
        head = null;
        createLinkedList(values);
    }

    // Function to create a linked list from a list of values
    private void createLinkedList(List<Integer> values) {
        if (values.isEmpty()) {
            head = null;
            return;
        }

        head = new List1Node(values.get(0));
        List1Node current = head;
        for (int i = 1; i < values.size(); i++) {
            current.next = new List1Node(values.get(i));
            current = current.next;
        }
    }

}

class Print1List{
    // Function to display the linked list
    public static void display(List1Node head) {
        List1Node current = head;
        while (current != null) {
            System.out.print(current.val + " -> ");
            current = current.next;
        }
        System.out.println("None");
    }
}

class Solution1 {
    public List1Node mergeTwoLists(List1Node list1, List1Node list2) {
        // Create a dummy node to simplify edge cases
        List1Node dummy = new List1Node(0);
        // Tail pointer tracks the end of the merged list
        List1Node tail = dummy;

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
        Solution1 sol = new Solution1();

        int[][][] testCases = {
                {{2, 5, 7}, {1, 3, 6, 8}},
                {{1, 1, 1}, {1, 1, 1}},
                {{-10, -5, 0, 5}, {-7, -3, 2, 10}},
                {{100}, {-100}},
                {{0, 10, 20, 30, 40, 50}, {5, 15, 25, 35, 45}}
        };

        int y = 1;
        for (int[][] tc : testCases) {
            List1Node list1 = buildLinkedList(tc[0]);
            List1Node list2 = buildLinkedList(tc[1]);
            List1Node mergedHead = sol.mergeTwoLists(list1, list2);
            List<Integer> result = toList(mergedHead);
            System.out.println(y++ + ".\tlist1: " + Arrays.toString(tc[0]));
            System.out.println("\tlist2: " + Arrays.toString(tc[1]));
            System.out.println("\n\tResult: " + result);
            System.out.println("-".repeat(100));
        }
    }

    private static List1Node buildLinkedList(int[] arr) {
        if (arr.length == 0) return null;
        List1Node head = new List1Node(arr[0]);
        List1Node curr = head;
        for (int i = 1; i < arr.length; i++) {
            curr.next = new List1Node(arr[i]);
            curr = curr.next;
        }
        return head;
    }

    private static List<Integer> toList(List1Node head) {
        List<Integer> result = new ArrayList<>();
        while (head != null) {
            result.add(head.val);
            head = head.next;
        }
        return result;
    }
}

public class MergeTwoLists {
    public static void main(String[] args) {
        Solution1.main(args);
    }
}
