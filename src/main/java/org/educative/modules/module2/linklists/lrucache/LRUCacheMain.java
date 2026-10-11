package org.educative.modules.module2.linklists.lrucache;

import java.util.*;

public class LRUCacheMain {
    private static class Node {
        int key;
        int value;
        Node prev;
        Node next;
        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.prev=null;
            this.next=null;
        }
        public Node() {
            this(0,0);
        }
    }

    /**
     * Time Complexity: O(1) for both get and put operations.
     * Space Complexity: O(capacity) for storing the cache.
     *
     * The LRUCache class implements a Least Recently Used (LRU) cache using a combination of a HashMap and a doubly linked list.
     * The HashMap provides O(1) access to cache entries, while the doubly linked list maintains the order of usage, allowing for efficient eviction of the least recently used items when the cache reaches its capacity.
     */
    private static class LRUCache {
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

    public static List<Object> runLRUCache(String[] operations, int[][] arguments) {
        List<Object> results = new ArrayList<>();
        LRUCache cache = null;

        for (int i = 0; i < operations.length; i++) {
            String op = operations[i];
            int[] args = arguments[i];

            switch (op) {
                case "LRUCache":
                    cache = new LRUCache(args[0]);
                    results.add(null);
                    break;
                case "get":
                    results.add(cache.get(args[0]));
                    break;
                case "put":
                    cache.put(args[0], args[1]);
                    results.add(null);
                    break;
            }
        }

        return results;
    }

    public static void main(String[] args) {
        int[][][] testCases = {
                {{1}, {5, 100}, {6, 200}, {5}, {6}},
                {{3}, {10, 10}, {20, 20}, {30, 30}, {10}, {40, 40}, {20}, {30}, {40}},
                {{2}, {7, 70}, {7, 77}, {7}, {8, 80}, {7}, {8}},
                {{2}, {1, 10}, {2, 20}, {1}, {3, 30}, {1}, {2}},
                {{3}, {100, 1}, {200, 2}, {300, 3}, {200}, {400, 4}, {500, 5}, {100}, {200}, {400}, {500}}
        };

        String[][] operations = {
                {"LRUCache", "put", "put", "get", "get"},
                {"LRUCache", "put", "put", "put", "get", "put", "get", "get", "get"},
                {"LRUCache", "put", "put", "get", "put", "get", "get"},
                {"LRUCache", "put", "put", "get", "put", "get", "get"},
                {"LRUCache", "put", "put", "put", "get", "put", "put", "get", "get", "get", "get"}
        };

        int y = 1;
        for (int t = 0; t < testCases.length; t++) {
            LRUCache cache = null;
            List<String> results = new ArrayList<>();

            for (int op = 0; op < operations[t].length; op++) {
                String operation = operations[t][op];
                int[] opArgs = testCases[t][op]; // FIXED: args → opArgs

                switch (operation) {
                    case "LRUCache":
                        cache = new LRUCache(opArgs[0]);
                        results.add("null");
                        break;
                    case "get":
                        results.add(String.valueOf(cache.get(opArgs[0])));
                        break;
                    case "put":
                        cache.put(opArgs[0], opArgs[1]);
                        results.add("null");
                        break;
                }
            }

            System.out.println(y++ + ".\tInput operations: " + Arrays.toString(operations[t]));
            System.out.println("\tInput arguments: " + Arrays.deepToString(testCases[t]));
            System.out.println("\tResult: [" + String.join(", ", results) + "]");
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
 }