package org.educative.modules.module2.linklists.circular;

import java.util.ArrayList;
import java.util.List;

/**
 * A singly linked list whose tail points back to its head.
 *
 * <p>An empty list has a {@code null} head; a one-node list points its node
 * back to itself.</p>
 */
public class CircularLinkedListMain {
    private static class ListNode {
        int data;
        ListNode next;

        public ListNode(int data) {
            this.data = data;
            this.next = null;
        }
    }

    /**
     * Demonstrates basic circular-list operations.
     */
    private static class CircularLinkedList {

        private ListNode head;

        /**
         * Creates an empty circular linked list.
         */
        public CircularLinkedList() {
            this.head = null;
        }

        /**
         * Appends a value to the list.
         *
         * @param value the value to append
         */
        public void insertAtEnd(int value) {
            ListNode newNode = new ListNode(value);
            if (this.head == null) {
                newNode.next = newNode;
                this.head = newNode;
            } else {
                ListNode current = this.head;
                while (current.next != this.head) {
                    current = current.next;
                }
                current.next = newNode;
                newNode.next = this.head;
            }
        }

        /**
         * Prepends a value to the list.
         *
         * @param value the value to prepend
         */
        public void insertAtBeginning(int value) {
            ListNode newNode = new ListNode(value);
            if (this.head == null) {
                newNode.next = this.head;
                this.head = newNode;
            } else {
                ListNode current = this.head;
                while (current.next != this.head) {
                    current = current.next;
                }
                newNode.next = this.head;
                current.next = newNode;
                this.head = newNode;
            }
        }

        /**
         * Inserts a value at a zero-based position.
         *
         * @param value    the value to insert
         * @param position the position at which to insert the value
         * @throws IndexOutOfBoundsException if the position is negative or beyond the list
         */
        public void insertAtPosition(int value, int position) {
            if (position < 0) {
                throw new IndexOutOfBoundsException("Negative positions are not allowed");
            }

            if (position == 0) {
                insertAtBeginning(value);
                return;
            }

            ListNode newNode = new ListNode(value);
            ListNode current = head;

            int index = 0;
            while (index < position - 1) {
                current = current.next;
                if (current == head) {
                    throw new IndexOutOfBoundsException("Position is out of bounds");
                }
                index++;
            }

            newNode.next = current.next;
            current.next = newNode;
        }

        /**
         * Inserts a value immediately after the first node containing {@code target}.
         * If the list is empty or the target is absent, the list is unchanged.
         *
         * @param target the value to search for
         * @param data   the value to insert after the target
         */
        public void insertAfter(int target, int data) {
            if (head == null) {
                return;
            }

            ListNode current = head;

            while (true) {
                if (current.data == target) {
                    ListNode newNode = new ListNode(data);
                    newNode.next = current.next;
                    current.next = newNode;
                    return;
                }
                current = current.next;
                if (current == head) {
                    break;
                }
            }
        }

        /**
         * Removes the first node.
         *
         * @return {@code true} when the node is removed
         * @throws IllegalStateException if the list is empty
         */
        public boolean deleteFromBeginning() {
            if (head == null) {
                throw new IllegalStateException("List is empty");
            }
            if (head.next == head) {
                head = null;
                return true;
            }
            ListNode current = head;
            while (current.next != head) {
                current = current.next;
            }
            head = head.next;
            current.next = head;
            return true;
        }

        /**
         * Removes the tail node while preserving the circular link to the head.
         *
         * @return {@code true} when the node is removed
         * @throws IllegalStateException if the list is empty
         */
        public boolean deleteFromEnd() {
            if (head == null) {
                throw new IllegalStateException("List is empty");
            }
            if (head.next == head) {
                head = null;
                return true;
            }
            ListNode current = head;
            while (current.next.next != head) {
                current = current.next;
            }
            ListNode deleteNode = current.next;
            current.next = head;
            deleteNode.next = null;
            return true;
        }

        /**
         * Removes the first node containing the specified value.
         *
         * @param value the value to remove
         * @return {@code true} if a matching node was removed; otherwise {@code false}
         * @throws IllegalStateException if the list is empty
         */
        public boolean deleteByValue(int value) {
            if (head == null) {
                throw new IllegalStateException("List is empty");
            }
            if (head.data == value) {
                head = head.next;
                return true;
            }
            ListNode current = head;
            ListNode prev = null;
            while (current.next != head && current.data != value) {
                prev = current;
                current = current.next;
            }
            // Check the tail when traversal reaches it without finding a match.
            if (current.data != value) {
                return false;
            }
            prev.next = current.next;
            return true;
        }

        /**
         * Removes the node at a zero-based position.
         *
         * @param position the position of the node to remove
         * @return {@code true} when the node is removed
         * @throws IllegalArgumentException if the position is negative or out of range
         * @throws IllegalStateException    if the list is empty
         */
        public boolean deleteByPosition(int position) {
            if (position < 0) {
                throw new IllegalArgumentException("Position must be non-negative");
            }

            if (head == null) {
                throw new IllegalStateException("List is empty");
            }

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
            if (current == null || current.next == null) {
                throw new IllegalArgumentException("Position out of range");
            }
            current.next = current.next.next;
            return true;
        }

        /**
         * Prints the list values in order, followed by an indication of the circular link.
         */
        public void traverse() {
            if (head == null) {
                System.out.println("List is empty");
                return;
            }
            ListNode current = head;
            while (true) {
                System.out.print(current.data + " -> ");
                current = current.next;
                if (current == head) {
                    break;
                }
            }
            System.out.println(current.data + " -> back to head");
        }

        /**
         * Returns the number of nodes in the list.
         *
         * @return the number of nodes, or {@code 0} if the list is empty
         */
        public int count() {
            if (head == null) {
                return 0;
            }
            ListNode current = head;
            int count = 1;
            while (current.next != head) {
                count++;
                current = current.next;
            }
            return count;
        }

        /**
         * Determines whether the list contains the specified value.
         *
         * @param value the value to search for
         * @return {@code true} if the value is present; otherwise {@code false}
         */
        public boolean exists(int value) {
            ListNode current = head;
            while (current.next != head) {
                if (current.data == value) {
                    return true;
                }
                ;
                current = current.next;
            }
            return false;
        }

        /**
         * Returns the value stored in the tail node.
         *
         * @return the tail value
         * @throws IllegalStateException if the list is empty
         */
        public int last() {
            ListNode current = head;
            if (current == null) {
                throw new IllegalStateException("List is empty");
            }
            while (current.next != head) {
                current = current.next;
            }
            return current.data;
        }

        /**
         * Copies the list values into a new list, starting at the head.
         *
         * @return a list containing the node values in traversal order
         */
        public List<Integer> toList() {
            List<Integer> list = new ArrayList<>();
            ListNode current = head;
            while (current != null) {
                list.add(current.data);
                current = current.next;
            }
            return list;
        }
    }

    public static void main(String[] args) {
        CircularLinkedList sampleList = new CircularLinkedList();

        ListNode first = new ListNode(10);
        ListNode second = new ListNode(20);
        ListNode third = new ListNode(30);

        sampleList.head = first;
        first.next = second;
        second.next = third;

        third.next = sampleList.head;

        System.out.println("Head node value: " + sampleList.head.data);
        System.out.println("Second node value: " + sampleList.head.next.data);
        System.out.println("Third node value: " + sampleList.head.next.next.data);
        System.out.println("-".repeat(100));

        System.out.println("Node after third: " + sampleList.head.next.next.next.data);
        System.out.println("-".repeat(100));

        System.out.println("Does third.next point back to first? " + (third.next == sampleList.head));
        System.out.println("-".repeat(100));

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
