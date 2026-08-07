package org.educative.modules.module2.linklists.allone;

import java.util.*;

class Node {
    int count;
    Set<String> keys;
    Node prev;
    Node next;

    public Node(int count) {
        this.count = count;
        this.keys = new HashSet<>();
        this.prev = null;
        this.next = null;
    }
}

class AllOne {
    private Map<String, Integer> keyCount;
    private Map<Integer, Node> countNode;
    private Node head;
    private Node tail;

    public AllOne() {
        keyCount = new HashMap<>();
        countNode = new HashMap<>();

        // Dummy head and tail sentinel nodes
        head = new Node(Integer.MIN_VALUE);
        tail = new Node(Integer.MAX_VALUE);
        head.next = tail;
        tail.prev = head;
    }

    public void inc(String key) {
        int cnt = keyCount.getOrDefault(key, 0);
        keyCount.put(key, cnt + 1);
        Node curr = countNode.getOrDefault(cnt, null);
        Node nextNode;

        if (countNode.containsKey(cnt + 1)) {
            nextNode = countNode.get(cnt + 1);
        } else {
            nextNode = insertNodeAfter(curr != null ? curr : head, cnt + 1);
        }

        nextNode.keys.add(key);

        if (curr != null) {
            curr.keys.remove(key);
            if (curr.keys.isEmpty()) {
                removeNode(curr);
            }
        }
    }

    public void dec(String key) {
        int cnt = keyCount.get(key);
        Node curr = countNode.get(cnt);
        keyCount.put(key, cnt - 1);

        if (keyCount.get(key) == 0) {
            keyCount.remove(key);
        }

        if (keyCount.containsKey(key)) {
            Node prevNode;
            if (countNode.containsKey(cnt - 1)) {
                prevNode = countNode.get(cnt - 1);
            } else {
                prevNode = insertNodeAfter(curr.prev, cnt - 1);
            }
            prevNode.keys.add(key);
        }

        curr.keys.remove(key);
        if (curr.keys.isEmpty()) {
            removeNode(curr);
        }
    }

    public String getMaxKey() {
        if (tail.prev != head && !tail.prev.keys.isEmpty()) {
            return tail.prev.keys.iterator().next();
        }
        return "";
    }

    public String getMinKey() {
        if (head.next != tail && !head.next.keys.isEmpty()) {
            return head.next.keys.iterator().next();
        }
        return "";
    }

    private Node insertNodeAfter(Node curr, int cnt) {
        Node newNode = new Node(cnt);
        newNode.prev = curr;
        newNode.next = curr.next;
        curr.next.prev = newNode;
        curr.next = newNode;
        countNode.put(cnt, newNode);
        return newNode;
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        countNode.remove(node.count);
    }

    public static void main(String[] args) {
        List<List<List<String>>> testCases = Arrays.asList(
                Arrays.asList(
                        Arrays.asList("AllOne", "inc", "inc", "inc", "getMaxKey", "getMinKey"),
                        Arrays.asList("", "apple", "banana", "apple", "", "")
                ),
                Arrays.asList(
                        Arrays.asList("AllOne", "inc", "inc", "inc", "dec", "getMaxKey", "getMinKey"),
                        Arrays.asList("", "dog", "dog", "cat", "dog", "", "")
                ),
                Arrays.asList(
                        Arrays.asList("AllOne", "inc", "dec", "getMaxKey", "getMinKey"),
                        Arrays.asList("", "x", "x", "", "")
                ),
                Arrays.asList(
                        Arrays.asList("AllOne", "inc", "inc", "inc", "inc", "getMaxKey", "getMinKey"),
                        Arrays.asList("", "a", "b", "b", "a", "", "")
                ),
                Arrays.asList(
                        Arrays.asList("AllOne", "inc", "inc", "dec", "dec", "inc", "getMaxKey", "getMinKey"),
                        Arrays.asList("", "a", "b", "a", "b", "c", "", "")
                )
        );

        int caseNum = 1;
        for (List<List<String>> tc : testCases) {
            List<String> ops    = tc.get(0);
            List<String> params = tc.get(1);

            System.out.println("Test Case " + caseNum++ + ":");
            System.out.println("\tOperations: " + ops);
            System.out.println("\tParameters: " + params + "\n");

            AllOne ds = new AllOne();
            List<String> result = new ArrayList<>();
            result.add("null");
            System.out.println("\tAllOne()");

            for (int i = 1; i < ops.size(); i++) {
                String op  = ops.get(i);
                String key = params.get(i);

                switch (op) {
                    case "inc":
                        ds.inc(key);
                        System.out.println("\tinc(\"" + key + "\")");
                        result.add("null");
                        break;
                    case "dec":
                        ds.dec(key);
                        System.out.println("\tdec(\"" + key + "\")");
                        result.add("null");
                        break;
                    case "getMaxKey":
                        String maxKey = ds.getMaxKey();
                        System.out.println("\tgetMaxKey() -> \"" + maxKey + "\"");
                        result.add("\"" + maxKey + "\"");
                        break;
                    case "getMinKey":
                        String minKey = ds.getMinKey();
                        System.out.println("\tgetMinKey() -> \"" + minKey + "\"");
                        result.add("\"" + minKey + "\"");
                        break;
                }
            }

            System.out.println("\n\tResult: [" + String.join(", ", result) + "]");
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}