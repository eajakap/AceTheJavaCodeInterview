package org.educative.modules.module2.linklists;

import java.util.*;

/**
 * Demonstrates an LRU (Least Recently Used) cache backed by a hash map and a
 * doubly linked list.
 */
public class LRUCacheMain {
    /** A cache entry and its links in the usage-order list. */
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

        /** Creates a sentinel node with no associated cache entry. */
        public Node() {
            this(0,0);
        }
    }

    /**
     * Stores key-value pairs and evicts the least recently used entry when full.
     * The map provides direct lookup, while the doubly linked list keeps the
     * most recently used entry immediately after the head sentinel and the
     * least recently used entry immediately before the tail sentinel.
     *
     * <p>{@code get} and {@code put} run in O(1) time; the cache uses O(capacity)
     * space.</p>
     */
    private static class LRUCache {
        HashMap<Integer, Node> cache;
        int capacity;
        private Node head;
        private Node tail;
        private int size;

        /**
         * Creates a cache with the specified maximum number of entries.
         *
         * @param capacity the maximum number of entries
         */
        public LRUCache(int capacity) {
            this.capacity = capacity;
            this.cache = new HashMap<>();
            this.size = 0;
            // Sentinels let list operations update neighboring nodes uniformly.
            this.head = new Node();
            this.tail = new Node();
            head.next = tail;
            tail.prev = head;
        }

        /** Unlinks a node from its current position in the usage-order list. */
        private void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        /** Links a node immediately after the head sentinel as most recently used. */
        private void addNodeToFront(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }

        /**
         * Returns the value for a key and marks its entry as most recently used.
         *
         * @param key the key to look up
         * @return the associated value, or {@code -1} if the key is absent
         */
        public int get(int key) {
            if (!cache.containsKey(key)) {
                return -1;
            }
            Node node = cache.get(key);
            removeNode(node);
            addNodeToFront(node);
            return node.value;
        }

        /**
         * Inserts or updates a key-value pair and marks it as most recently used.
         * If a new entry exceeds capacity, the least recently used entry is evicted.
         *
         * @param key the key to insert or update
         * @param value the value associated with the key
         */
        public void put(int key, int value) {
            if (!cache.containsKey(key)) {
                Node newNode = new Node(key, value);
                cache.put(key, newNode);
                addNodeToFront(newNode);
                size++;
                if (size > capacity) {
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

        /** Removes all key mappings and resets the tracked entry count. */
        public void clear() {
            cache.clear();
            size = 0;
            head.next = tail;
            tail.prev = head;
        }

        /**
         * Prints the LRU Cache
         * @return
         */
        public String printLRUCache() {
            StringBuilder sb = new StringBuilder();
            if (cache.isEmpty()) {
                return String.format("Cache is empty");
            }
            int size = cache.size(), count = 0;
            for (Node node : cache.values()) {
                count++;
                sb.append(node.key).append("=")
                        .append(node.value)
                        .append(count != size ? "," : "");
            }
            return sb.toString();
        }
    }

    /**
     * Executes a sequence of cache operations in the format used by coding
     * challenge test cases.
     *
     * @param operations operation names such as {@code LRUCache}, {@code get},
     *                    {@code put}, and {@code clear}
     * @param arguments arguments corresponding to each operation
     * @return one result per operation; constructor and {@code put} results are
     *         {@code null}, {@code get} results are integer values, and
     *         {@code clear} results are {@code "clear"}
     */
    public static List<Object> runLRUCache(String[] operations, int[][] arguments) {
        List<Object> results = new ArrayList<>();
        LRUCache cache = null;

        for (int i = 0; i < operations.length; i++) {
            String op = operations[i];
            int[] args = arguments[i];

            switch (op) {
                case "LRUCache":
                    cache = new LRUCache(args[0]);
                    results.add("capacity: {" + cache.capacity + "}");
                    break;
                case "get":
                    results.add("get: {" + args[0] + ": "+ cache.get(args[0]) + "}");
                    break;
                case "put":
                    cache.put(args[0], args[1]);
                    results.add("put: {" + args[0] + ": " + args[1] + "}");
                    break;
                case "print":
                    results.add("print: {" + cache.printLRUCache() + "}");
                    break;
                case "clear":
                    cache.clear();
                    results.add("clear");
                    break;
                default:
                    results.add("Invalid operation");
            }
        }

        return results;
    }

    /** Runs the built-in LRU cache examples and prints each input and result. */
    public static void main(String[] args) {
        // Each row of arguments supplies the constructor or method arguments
        // for the operation at the same index.
        int[][][] testCases = {
                //{{LRUCacheCapacity}, {putKey, putValue}, {putKey, putValue}, {getKey}, {getKey}, {clear}},
                {{1}, {5, 100}, {6, 200}, {5}, {6}, {}, {}},
                {{3}, {10, 10}, {20, 20}, {30, 30}, {10}, {40, 40}, {20}, {30}, {40}, {}, {}},
                {{2}, {7, 70}, {7, 77}, {7}, {8, 80}, {7}, {8}, {}, {}},
                {{2}, {1, 10}, {2, 20}, {1}, {3, 30}, {1}, {2}, {}, {}},
                {{3}, {100, 1}, {200, 2}, {300, 3}, {200}, {400, 4}, {500, 5}, {100}, {200}, {400}, {500}, {}, {}}
        };

        String[][] operations = {
                {"LRUCache", "put", "put", "get", "get", "print", "clear"},
                {"LRUCache", "put", "put", "put", "get", "put", "get", "get", "get", "print", "clear"},
                {"LRUCache", "put", "put", "get", "put", "get", "get", "print", "clear"},
                {"LRUCache", "put", "put", "get", "put", "get", "get", "print", "clear"},
                {"LRUCache", "put", "put", "put", "get", "put", "put", "get", "get", "get", "get", "print", "clear"}
        };

        int y = 1;
        for (int t = 0; t < testCases.length; t++) {
            System.out.println(y++ + ".\tInput operations: " + Arrays.toString(operations[t]));
            System.out.println("\tInput arguments: " + Arrays.deepToString(testCases[t]));
            List<Object> results = runLRUCache(operations[t], testCases[t]);
            System.out.println("\tResult: " + results);
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
 }