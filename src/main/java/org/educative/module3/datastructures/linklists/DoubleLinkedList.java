package org.educative.module3.datastructures.linklists;

import java.util.ArrayList;
import java.util.List;

class List2Node {
    int data;
    List2Node prev;
    List2Node next;

    public List2Node(int data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}

class DoublyLinkedList {
    List2Node head;

    public DoublyLinkedList() {
        this.head = null;
    }

    public void insertAtEnd(int value) {
        List2Node newNode = new List2Node(value);
        if (this.head == null) {
            this.head = newNode;
        } else  {
            List2Node current = this.head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
            newNode.prev = current;
        }
    }

    public void insertAtBeginning(int value) {
        List2Node newNode = new List2Node(value);
        if (this.head == null) {
            this.head = newNode;
        } else  {
            newNode.next = this.head;
            this.head.prev = newNode;
            this.head = newNode;
        }
    }

    public void insertAfterValue(int target, int data) {
        List2Node current = head;

        while (current != null && current.data != target) {
            current = current.next;       // Search for the target node
        }

        if (current == null) {
            return;                       // Target not found
        }

        List2Node newNode = new List2Node(data);

        newNode.next = current.next;      // Connect new node forward
        newNode.prev = current;           // Connect new node backward

        if (current.next != null) {
            current.next.prev = newNode;  // Update next node's prev reference
        }

        current.next = newNode;           // Link previous node to the new node
    }

    public void insertAtPosition(int value, int position) {
        if (position == 0) {
            insertAtBeginning(value);
            return;
        }
        List2Node newNode = new List2Node(value);
        List2Node  current = this.head;
        int index = 0;
        while (current != null && index < position - 1) {
            current = current.next;
            index++;
        }
        if (current == null) {
            throw new IndexOutOfBoundsException("Position is out of bounds");
        }
        // Update links in the correct order
        newNode.next = current.next;
        newNode.prev = current;
        if (current.next != null) {
            current.next.prev = newNode;  // Update next node's prev reference
        }
        current.next = newNode;
    }

    public boolean deleteFromBeginning() {
        if (head == null) {
            throw new IllegalStateException("List is empty");
        }
        head = head.next;
        if (head != null) {
            head.prev = null;  // Update the new head's prev reference
        }
        return true;
    }

    public void deleteByValue(int target) {
        List2Node current = head;

        // Traverse the list to find the target node
        while (current != null) {
            if (current.data == target) {

                if (current.prev == null) {          // Target is the head node
                    head = current.next;
                    if (head != null) {
                        head.prev = null;
                    }
                    return;
                }

                if (current.next != null) {
                    current.next.prev = current.prev;  // Update next node's prev
                }

                current.prev.next = current.next;      // Update previous node's next
                return;
            }

            current = current.next;
        }
    }

    public boolean deleteAtPosition(int position) {
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
        List2Node current = head;
        while (current != null && index < position - 1) {
            current = current.next;
            index++;
        }
        // If position is out of range, do nothing
        if (current == null || current.next == null) {
            throw new IllegalArgumentException("Position out of range");
        }
        if (current.prev == null) {      // Deleting the head node
            head = current.next;
            if (head != null) {
                head.prev = null;
            }
            return true;
        }

        // Bypass the node at 'position'
        if (current.next != null) {
            current.next.prev = current.prev;
        }
        current.prev.next = current.next;
        return true;
    }

    public void traverseForward() {
        List2Node current = head;
        while (current != null) {
            System.out.print(current.data + " -> ");  // Example operation: print the value
            current = current.next;
        }
        System.out.println("Reached front --> end");  // Indicate the end of the list
    }

    public void traverseBackward() {
        if (head == null) {
            throw new IllegalStateException("List is empty");
        }
        // go the the end of the list
        List2Node current = head;
        while (current.next != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.print(current.data + " -> ");
        System.out.println("Reached front --> end");

        while (current != null) {
            System.out.print(current.data + " <-- ");  // Example operation: print the value
            current = current.prev;
        }
        System.out.println("Reached end <-- front");  // Indicate the front of the list
    }

    public int count() {
        List2Node current = head;
        int count =0;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    public boolean exists(int value) {
        List2Node current = head;
        while (current != null) {
            if (current.data == value) {
                return true;
            };
            current = current.next;
        }
        return false;
    }

    public int last() {
        List2Node current = head;
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
        List2Node current = head;
        while (current != null) {
            list.add(current.data);
            current = current.next;
        }
        return list;
    }

}

public class DoubleLinkedList {
    public static void main(String[] args) {
        DoublyLinkedList sampleList = new DoublyLinkedList();

        // Create nodes
        List2Node first = new List2Node(10);
        List2Node second = new List2Node(20);
        List2Node third = new List2Node(30);

        // Connect nodes (forward and backward links)
        sampleList.head = first;

        first.next = second;
        second.prev = first;

        second.next = third;
        third.prev = second;

        // Print values (forward traversal using head)
        System.out.println("First node value: " + sampleList.head.data);
        System.out.println("Second node value: " + sampleList.head.next.data);
        System.out.println("Third node value: " + sampleList.head.next.next.data);
        System.out.println("-".repeat(100));

        // Show backward links
        System.out.println("\nPrevious of second node: " + sampleList.head.next.prev.data);
        System.out.println("Previous of third node: " + sampleList.head.next.next.prev.data);
        System.out.println("-".repeat(100));

        // Show end links
        System.out.println("\nPrevious of first node: " + sampleList.head.prev);
        System.out.println("Next of third node: " + sampleList.head.next.next.next);

        // Show the difference between forward and backward traversal
        System.out.print("Forward traversal: ");
        sampleList.traverseForward();

        System.out.print("\nBackward traversal: ");
        sampleList.traverseBackward();

        System.out.println("Initial list:");
        sampleList.traverseForward();

        System.out.println("\nInsert 20 at beginning:");
        sampleList.insertAtBeginning(20);
        sampleList.traverseForward();

        System.out.println("\nInsert 10 at beginning:");
        sampleList.insertAtBeginning(10);
        sampleList.traverseForward();

        System.out.println("\nInsert 30 at end:");
        sampleList.insertAtEnd(30);
        sampleList.traverseForward();

        System.out.println("\nInsert 25 after 20:");
        sampleList.insertAfterValue(20, 25);
        sampleList.traverseForward();

        System.out.println("\nDelete from beginning:");
        sampleList.deleteFromBeginning();
        sampleList.traverseForward();

        System.out.println("\nDelete by value (25):");
        sampleList.deleteByValue(25);
        sampleList.traverseForward();

        System.out.println("\nDelete at position 1:");
        sampleList.deleteAtPosition(1);
        sampleList.traverseForward();

    }
}


