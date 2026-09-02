package org.educative.modules.module2.linklists.single;

import java.util.ArrayList;
import java.util.List;


public class SingleLinkedList {
    static class ListNode {
        int data;
        ListNode next;

        public ListNode(int data) {
            this.data = data;
            this.next = null;
        }
    }

    static class LinkedList {
        ListNode head;

        public LinkedList() {
            this.head = null;
        }

        public LinkedList(List<Integer> list) {
            this.head = null;
            createLinkedList(list);
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

        private ListNode lastNode() {
            ListNode current = head;
            if (current == null) {
                throw new IllegalStateException("List is empty");
            }
            while (current.next != null) {
                current = current.next;
            }
            return current;
        }

        private ListNode findNodeAtPosition(int position) {
            ListNode current = head;
            if (current == null) {
                throw new IllegalStateException("List is empty");
            }
            int index = 0;
            while (current != null && index < position) {
                current = current.next;
                index++;
            }
            if (current == null) {
                throw new IllegalArgumentException("Position out of range");
            }
            return current;
        }

        public void insertAtEnd(int value) {
            ListNode newNode = new ListNode(value);
            if (this.head == null) {
                this.head = newNode;
            } else  {
                lastNode().next = newNode;
            }
        }

        public void insertAtBeginning(int value) {
            ListNode newNode = new ListNode(value);
            if (this.head == null) {
                this.head = newNode;
            } else  {
                newNode.next = this.head;
                this.head = newNode;
            }
        }

        public void insertAtPosition(int value, int position) {
            if (position == 0) {
                insertAtBeginning(value);
                return;
            }
            ListNode newNode = new ListNode(value);
            ListNode  current = findNodeAtPosition(position - 1);

            if (current == null) {
                throw new IndexOutOfBoundsException("Position is out of bounds");
            }
            // Update links in the correct order
            newNode.next = current.next;
            current.next = newNode;
        }

        public boolean deleteAtBegin() {
            if (head == null) {
                throw new IllegalStateException("List is empty");
            }
            head = head.next;
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
                deleteAtBegin();
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
            ListNode current = head;
            while (current != null) {
                System.out.print(current.data + " -> ");  // Example operation: print the value
                current = current.next;
            }
            System.out.println("null");  // Indicate the end of the list
        }

        public int count() {
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
    }

    static class LinkedListWithTail {
        ListNode head;
        ListNode tail;

        public LinkedListWithTail() {
            this.head = null;
            this.tail = null;
        }

        public void insertAtBegin(int value) {
            ListNode newNode = new ListNode(value);
            if (this.head == null) {
                this.head = newNode;
                this.tail = newNode;
            } else  {
                newNode.next = this.head;
                this.head = newNode;
            }
        }

        public void insertAtEnd(int value) {
            ListNode newNode = new ListNode(value);
            if (this.head == null) {
                this.head = newNode;
                this.tail = newNode;
            } else  {
                this.tail.next = newNode;
                this.tail = newNode;
            }
        }

        public void insertAtPosition(int value, int position) {
            if (position == 0) {
                insertAtBegin(value);
                return;
            }
            // Traverse to the node just before the desired position
            ListNode newNode = new ListNode(value);
            ListNode  current = this.head;
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
            current.next = newNode;
            if (current == this.tail) {
                tail.next = newNode;
                tail = newNode;
            }
        }

        public void traverse() {
            ListNode current = head;
            while (current != null) {
                System.out.print(current.data + " -> ");  // Example operation: print the value
                current = current.next;
            }
            System.out.println("null");  // Indicate the end of the list
        }

        public int count() {
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

        public boolean deleteAtBegin() {
            if (head == null) {
                throw new IllegalStateException("List is empty");
            }
            head = head.next;
            if (head == null) { // If the list becomes empty, update tail as well
                tail = null;
            }
            return true;
        }

        public boolean deleteByValue(int value) {
            if (head == null) {
                throw new IllegalStateException("List is empty");
            }
            // If the head node matches, delete it by moving head forward
            if (head.data == value) {
                head = head.next;
                if (head == null) { // If the list becomes empty, update tail as well
                    tail = null;
                }
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
            if (current.next == null) { // If the deleted node was the tail, update tail
                tail = prev;
            }
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
                deleteAtBegin();
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
            if (current.next == null) { // If the deleted node was the tail, update tail
                tail = current;
            }
            return true;
        }


    }

    public static void main(String[] args) {
        // Create a linked list object
        LinkedList sampleList = new LinkedList();

        // Create nodes
        ListNode first = new ListNode(10);
        ListNode second = new ListNode(20);
        ListNode third = new ListNode(30);


        // Connect nodes
        sampleList.head = first;
        first.next = second;
        second.next = third;

        // Print values
        System.out.println("Head node value: " + sampleList.head.data);
        System.out.println("Second node value: " + sampleList.head.next.data);
        System.out.println("Next of second node: " + sampleList.head.next.next);

        // traversal
        sampleList.traverse();
        System.out.println("Number of nodes: " + sampleList.count());
        System.out.println("Does node with value 10 exist? " + sampleList.exists(10));
        System.out.println("Does node with value 20 exist? " + sampleList.exists(20));
        System.out.println("Does node with value 30 exist? " + sampleList.exists(30));
        System.out.println("Linked list as a list: " + sampleList.toList());
        System.out.println("Last node value: " + sampleList.last());

        LinkedListWithTail sampleListWithTail = new LinkedListWithTail();
        sampleListWithTail.insertAtEnd(10);
        sampleListWithTail.insertAtBegin(20);
        sampleListWithTail.insertAtEnd(30);
        sampleListWithTail.traverse();
        System.out.println("Linked list as a list: " + sampleListWithTail.head.data);
        System.out.println("Last node value: " + sampleListWithTail.last());
        sampleListWithTail.insertAtPosition(55,3);
        sampleListWithTail.traverse();
        sampleListWithTail.deleteByValue(20);
        sampleListWithTail.traverse();
        sampleListWithTail.deleteByPosition(1);
        sampleListWithTail.traverse();
    }
}

