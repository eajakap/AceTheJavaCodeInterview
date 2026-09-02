package org.educative.modules.module2.linklists.lrucache;

import java.util.HashMap;

/**
 * Time Complexity: O(1) for both get and put operations.
 * Space Complexity: O(capacity) for storing the cache.
 *
 * The LRUCache class implements a Least Recently Used (LRU) cache using a combination of a HashMap and a doubly linked list.
 * The HashMap provides O(1) access to cache entries, while the doubly linked list maintains the order of usage, allowing for efficient eviction of the least recently used items when the cache reaches its capacity.
 */
public class LRUCache {
    HashMap<Integer, Node> cache;
    int capacity;
    private Node head;
    private Node tail;
    private int size;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.size = 0;
        // Dummy head and tail for doubly linked list
        this.head = new Node();
        this.tail = new Node();
        head.next = tail;
        tail.prev = head;
    }

    private void removeNode(Node node) {
        // Remove node from its current position in the linked list
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void addNodeToFront(Node node) {
        // Insert node right after head (most recently used position)
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        Node node = cache.get(key);
        removeNode(node);
        addNodeToFront(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (!cache.containsKey(key)) {
            // Create new node and add to front
            Node newNode = new Node(key, value);
            cache.put(key, newNode);
            addNodeToFront(newNode);
            size++;
            // If over capacity, evict LRU (node before tail)
            if (isCapacityFull()) {
                Node lru = tail.prev;
                removeNode(lru);
                cache.remove(lru.key);
                size--;
            }
        } else {
            Node node = cache.get(key);
            node.value = value;
            removeNode(node);
            addNodeToFront(node);
        }
    }
    public boolean isCapacityFull() {
        return size >= capacity;
    }

    public void clear() {
        cache.clear();
        size = 0;
    }
}
