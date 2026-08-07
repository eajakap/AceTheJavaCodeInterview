package org.educative.modules.module3.linklists.reverse;

import java.util.ArrayList;
import java.util.List;

class List4Node {
    int data;
    List4Node next;

    public List4Node(int data) {
        this.data = data;
        this.next = null;
    }
}

class Linked4List {
    List4Node head;

    public Linked4List() {
        this.head = null;
    }

    public void insertAtEnd(int value) {
        List4Node newNode = new List4Node(value);
        if (this.head == null) {
            this.head = newNode;
        } else  {
            List4Node current = this.head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public void insertAtBeginning(int value) {
        List4Node newNode = new List4Node(value);
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
        List4Node newNode = new List4Node(value);
        List4Node  current = this.head;
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
        List4Node current = head;
        List4Node prev = null;
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
        List4Node current = head;
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

    public Linked4List reverse() {
        List4Node prev = null;
        List4Node current = head;
        while (current != null) {
            List4Node nextNode = current.next;
            current.next = prev;
            prev = current;
            System.out.print(current.data + " -> ");  // Example operation: print the value
            current = nextNode;
        }
        System.out.println("null");  // Indicate the end of the list
        // prev now points to the new head (last node of original list)
        Linked4List reversedList = new Linked4List();
        reversedList.head = prev;
        return reversedList;
    }

    public void traverse() {
        List4Node current = head;
        while (current != null) {
            List4Node nextNode = current.next;
            System.out.print(current.data + " -> ");  // Example operation: print the value
            current = current.next;
        }
        System.out.println("null");  // Indicate the end of the list
    }

    public int count() {
        List4Node current = head;
        int count =0;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    public boolean exists(int value) {
        List4Node current = head;
        while (current != null) {
            if (current.data == value) {
                return true;
            };
            current = current.next;
        }
        return false;
    }

    public int last() {
        List4Node current = head;
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
        List4Node current = head;
        while (current != null) {
            list.add(current.data);
            current = current.next;
        }
        return list;
    }
}

class Linked4ListWithTail {
    List4Node head;
    List4Node tail;

    public Linked4ListWithTail() {
        this.head = null;
        this.tail = null;
    }

    public void insertAtBegin(int value) {
        List4Node newNode = new List4Node(value);
        if (this.head == null) {
            this.head = newNode;
            this.tail = newNode;
        } else  {
            newNode.next = this.head;
            this.head = newNode;
        }
    }

    public void insertAtEnd(int value) {
        List4Node newNode = new List4Node(value);
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
        List4Node newNode = new List4Node(value);
        List4Node  current = this.head;
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

    public void reverse() {
        List4Node current = head;
        while (current != null) {
            System.out.print(current.data + " -> ");  // Example operation: print the value
            current = current.next;
        }
        System.out.println("null");  // Indicate the end of the list
    }

    public void traverse() {
        List4Node current = head;
        while (current != null) {
            System.out.print(current.data + " -> ");  // Example operation: print the value
            current = current.next;
        }
        System.out.println("null");  // Indicate the end of the list
    }

    public int count() {
        List4Node current = head;
        int count =0;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    public boolean exists(int value) {
        List4Node current = head;
        while (current != null) {
            if (current.data == value) {
                return true;
            };
            current = current.next;
        }
        return false;
    }

    public int last() {
        List4Node current = head;
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
        List4Node current = head;
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
        List4Node current = head;
        List4Node prev = null;
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
        List4Node current = head;
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

public class ReverseLinkedList {
    public static void main(String[] args) {
        // Create a linked list object
        Linked4List sampleList = new Linked4List();

        // Create nodes
        List4Node first = new List4Node(10);
        List4Node second = new List4Node(20);
        List4Node third = new List4Node(30);


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
        Linked4List reversedHead = sampleList.reverse();
        System.out.println("Linked list as a list: " + reversedHead.toList());
        System.out.println("Last node value: " + reversedHead.last());
        // Print formatted output
        System.out.println("-".repeat(100));

//        Linked4ListWithTail sampleListWithTail = new Linked4ListWithTail();
//        sampleListWithTail.insertAtEnd(10);
//        sampleListWithTail.insertAtBegin(20);
//        sampleListWithTail.insertAtEnd(30);
//        sampleListWithTail.traverse();
//        System.out.println("Linked list as a list: " + sampleListWithTail.head.data);
//        System.out.println("Last node value: " + sampleListWithTail.last());
//        sampleListWithTail.insertAtPosition(55,3);
//        sampleListWithTail.traverse();
//        sampleListWithTail.deleteByValue(20);
//        sampleListWithTail.traverse();
//        sampleListWithTail.deleteByPosition(1);
//        sampleListWithTail.traverse();
    }
}

