package org.educative.modules.module2.linklists.circular;

import java.util.ArrayList;
import java.util.List;

public class CircularLinkedList {
    private static class ListNode {
        int data;
        ListNode next;

        public ListNode(int data) {
            this.data = data;
            this.next = null;
        }
    }
    
    private ListNode head;

    public CircularLinkedList() {
        this.head = null;
    }

    public void insertAtEnd(int value) {
        ListNode newNode = new ListNode(value);
        if (this.head == null) {
            newNode.next = newNode; // Point to itself to form a circular link
            this.head = newNode;
        } else  {
            ListNode current = this.head;
            while (current.next != this.head) {
                current = current.next;
            }
            current.next = newNode;
            newNode.next = this.head; // Complete the circular link
        }
    }

    public void insertAtBeginning(int value) {
        ListNode newNode = new ListNode(value);
        if (this.head == null) {
            newNode.next = this.head; // Point to itself to form a circular link
            this.head = newNode;
        } else  {
            // go through to the end node
            ListNode current = this.head;
            while (current.next != this.head) {
                current = current.next;
            }
            // here i am at end node
            newNode.next = this.head;
            current.next = newNode;
            this.head = newNode;
        }
    }

    public void insertAtPosition(int value, int position) {
        if (position < 0) {
            throw new IndexOutOfBoundsException("Negative positions are not allowed");
        }

        // Case 1: insert at beginning
        if (position == 0) {
            insertAtBeginning(value);
            return;
        }

        // Case 2: insert somewhere after head
        ListNode newNode = new ListNode(value);
        ListNode current = head;

        int index = 0;
        // Traverse to position - 1
        while (index < position - 1) {
            current = current.next;
            // If we loop back to head before reaching position - 1
            if (current == head) {
                throw new IndexOutOfBoundsException("Position is out of bounds");
            }
            index++;
        }

        // Insert newNode after current
        newNode.next = current.next;
        current.next = newNode;
    }

    public void insertAfter(int target, int data) {
        if (head == null) {
            return;
        }

        ListNode current = head;

        while (true) {
            if (current.data == target) {
                ListNode newNode = new ListNode(data);   // Create the new node
                newNode.next = current.next;             // New node points to the next node
                current.next = newNode;                  // Target node points to the new node
                return;
            }
            current = current.next;
            if (current == head) {                       // Target not found after one full cycle
                break;
            }
        }
    }

    public boolean deleteFromBeginning() {
        if (head == null) {
            throw new IllegalStateException("List is empty");
        }
        if (head.next == head) {
            // single node list
            head = null;
            return true;
        }
        // traverse through such that you cycle back to start
        ListNode current = head;
        while (current.next != head) {
            current = current.next;
        }
        head = head.next;
        current.next = head;
        return true;
    }

    public boolean deleteFromEnd() {
        if (head == null) {
            throw new IllegalStateException("List is empty");
        }
        if (head.next == head) {
            // single node list
            head = null;
            return true;
        }
        // traverse through such that you reach the 2nd last node
        ListNode current = head;
        while (current.next.next != head) {
            current = current.next;
        }
        //at 2nd last node
        ListNode deleteNode = current.next; // This is the last node
        current.next = head;
        deleteNode.next = null;
        return true;
    }

    public boolean deleteByValue(int value) {
        if (head == null) {
            throw new IllegalStateException("List is empty");
        }
        // If the head node matches, delete it by moving head forward
        if (head.data == value) {
            head = head.next;
            return true;
        }
        ListNode current = head;
        ListNode prev = null;
        while (current != null && current.data != value) {
            prev = current;
            current = current.next;
        }
        if (current == null) {
            return false;
        }
        prev.next = current.next;
        return true;
    }

    public boolean deleteByPosition(int position) {
        if (position < 0) {
            throw new IllegalArgumentException("Position must be non-negative");
        }

        // If the list is empty, nothing can be deleted
        if (head == null) {
            throw new IllegalStateException("List is empty");
        }

        // If the head node matches, delete it by moving head forward
        if (position == 0) {
            deleteFromBeginning();
            return true;
        }


        int index = 0;
        ListNode current = head;
        while (current != null && index < position - 1) {
            current = current.next;
            index++;
        }
        // If position is out of range, do nothing
        if (current == null || current.next == null) {
            throw new IllegalArgumentException("Position out of range");
        }
        // Bypass the node at 'position'
        current.next = current.next.next;
        return true;
    }

    public void traverse() {
        if (head == null) {
            System.out.println("List is empty");
            return;
        }
        ListNode current = head;
        while (true) {
            System.out.print(current.data + " -> ");   // Process current node
            current = current.next;                    // Move to the next node
            if (current == head) {                     // Stop when traversal returns to head
                break;
            }
        }
//        do {
//            System.out.print(current.data + " -> ");  // Example operation: print the value
//            current = current.next;
//        } while (current != head);
        System.out.println(current.data + " -> back to head");  // Indicate the end of the list
    }

    public int count() {
        if (head == null) {
            return 0;
        }
        ListNode current = head;
        int count =0;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    public boolean exists(int value) {
        ListNode current = head;
        while (current != null) {
            if (current.data == value) {
                return true;
            };
            current = current.next;
        }
        return false;
    }

    public int last() {
        ListNode current = head;
        if (current == null) {
            throw new IllegalStateException("List is empty");
        }
        while (current.next != null) {
            current = current.next;
        }
        return current.data;
    }

    public List<Integer> toList() {
        List<Integer> list = new ArrayList<>();
        ListNode current = head;
        while (current != null) {
            list.add(current.data);
            current = current.next;
        }
        return list;
    }

    public static void main(String[] args) {
        // Create a linked list object
        CircularLinkedList sampleList = new CircularLinkedList();

        // Create nodes
        ListNode first = new ListNode(10);
        ListNode second = new ListNode(20);
        ListNode third = new ListNode(30);

        // Connect nodes
        sampleList.head = first;
        first.next = second;
        second.next = third;

        // Make the list circular by connecting the last node back to the first
        third.next = sampleList.head;

        // Print values
        System.out.println("Head node value: " + sampleList.head.data);
        System.out.println("Second node value: " + sampleList.head.next.data);
        System.out.println("Third node value: " + sampleList.head.next.next.data);
        System.out.println("-".repeat(100));

        // Show the circular connection
        System.out.println("Node after third: " + sampleList.head.next.next.next.data);
        System.out.println("-".repeat(100));

        // Verify the loop more clearly
        System.out.println("Does third.next point back to first? " + (third.next == sampleList.head));
        System.out.println("-".repeat(100));

        // traversal
        System.out.print("Traversal of circular linked list: ");
        sampleList.traverse();
        System.out.println("-".repeat(100));

        System.out.println("Creating a circular linked list by inserting 10, 20, 30, and 40 at the end:");
        CircularLinkedList sampleList2 = new CircularLinkedList();
        sampleList2.insertAtEnd(10);
        sampleList2.insertAtEnd(20);
        sampleList2.insertAtEnd(30);
        sampleList2.insertAtEnd(40);
        System.out.println("Current list:");
        sampleList2.traverse();

        System.out.println("\nInserting 5 at the beginning:");
        sampleList2.insertAtBeginning(5);
        System.out.println("List after inserting at the beginning:");
        sampleList2.traverse();

        System.out.println("\nDeleting the first node:");
        sampleList2.deleteFromBeginning();
        System.out.println("List after deleting from the beginning:");
        sampleList2.traverse();

        System.out.println("\nDeleting the last node:");
        sampleList2.deleteFromEnd();
        System.out.println("List after deleting from the end:");
        sampleList2.traverse();

        System.out.println("\nDeleting the node with value 20:");
        sampleList2.deleteByValue(20);
        System.out.println("List after deleting value 20:");
        sampleList2.traverse();
    }
}
