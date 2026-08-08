package org.educative.modules.module2.graphs;

import java.util.*;

public class UnidirectedGraphWithBFS {
    public static void main(String[] args) throws Exception {
        Main.main(args);
    }
}

class Graph {
    private Map<String, List<String>> graph;   // Adjacency list

    public Graph() {
        this.graph = new HashMap<>();
    }

    public void addEdge(String u, String v) {
        // Initialize nodes if not present
        graph.putIfAbsent(u, new ArrayList<>());
        graph.putIfAbsent(v, new ArrayList<>());

        graph.get(u).add(v);
        graph.get(v).add(u);   // Remove for directed graph
    }

    public void bfs(String start) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        queue.add(start);
        visited.add(start);   // Mark start node as visited

        while (!queue.isEmpty()) {
            String vertex = queue.poll();   // Get next node
            System.out.print(vertex + " ");

            for (String neighbor : graph.get(vertex)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);    // Mark neighbor visited
                    queue.add(neighbor);      // Add neighbor to queue
                }
            }
        }
    }
}

class Main {
    public static void main(String[] args) {
        Graph g = new Graph();

        // Build graph
        g.addEdge("A", "B");
        g.addEdge("A", "C");
        g.addEdge("B", "D");
        g.addEdge("B", "E");
        g.addEdge("C", "F");

        // Run BFS
        g.bfs("A");
    }
}