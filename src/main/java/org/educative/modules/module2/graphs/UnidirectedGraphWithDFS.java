package org.educative.modules.module2.graphs;

import java.util.*;

public class UnidirectedGraphWithDFS {
    public static void main(String[] args) throws Exception {
        Main.main(args);
    }

    private static class Graph {
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

        public void dfsUsingStack(String start) {
            Set<String> visited = new HashSet<>();
            Deque<String> stack = new ArrayDeque<>();

            stack.push(start); // LIFO: push and pop from the top

            while (!stack.isEmpty()) {
                String vertex = stack.pop();

                if (visited.contains(vertex)) {
                    continue;
                }

                visited.add(vertex);
                System.out.print(vertex + " ");

                List<String> neighbors = graph.getOrDefault(vertex, Collections.emptyList());
                for (int i = neighbors.size() - 1; i >= 0; i--) {
                    String neighbor = neighbors.get(i);
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }

        public void dfs(String start) {
            Set<String> visited = new HashSet<>();
            dfsHelper(start, visited);
        }

        private void dfsHelper(String vertex, Set<String> visited) {
            visited.add(vertex);   // Mark current node as visited
            System.out.print(vertex + " ");

            for (String neighbor : graph.get(vertex)) {
                if (!visited.contains(neighbor)) {
                    dfsHelper(neighbor, visited);   // Visit unvisited neighbor
                }
            }
        }

    }

    private static class Main {
        public static void main(String[] args) {
            Graph g = new Graph();

            // Build graph
            g.addEdge("A", "B");
            g.addEdge("A", "C");
            g.addEdge("B", "D");
            g.addEdge("B", "E");
            g.addEdge("C", "F");

            // Run DFS
            g.dfs("A"); // using Recursion
            System.out.println("\nDFS Using stack");
            g.dfsUsingStack("A"); // using Stack
        }
    }

}