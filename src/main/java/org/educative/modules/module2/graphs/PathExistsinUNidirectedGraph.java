package org.educative.modules.module2.graphs;

import java.util.Arrays;
import java.util.*;

public class PathExistsinUNidirectedGraph {
    public static void main(String[] args) {
        Solution.main(args);
    }

    private static class Solution {
        public boolean validPath(int n, int[][] edges, int source, int destination) {
            // Build adjacency list
            Map<Integer, List<Integer>> adjacency = new HashMap<>();

            for (int[] edge : edges) {
                int a = edge[0];
                int b = edge[1];
                adjacency.computeIfAbsent(a, k -> new ArrayList<>()).add(b);
                adjacency.computeIfAbsent(b, k -> new ArrayList<>()).add(a);
            }

            Set<Integer> visited = new HashSet<>();
            Queue<Integer> queue = new LinkedList<>();
            queue.add(source);
            visited.add(source);

            while (!queue.isEmpty()) {
                int currentVertex = queue.poll();

                // Check if the dequeued vertex is the destination
                if (currentVertex == destination) {
                    return true;
                }

                // Explore neighbors of the current vertex
                List<Integer> neighbors = adjacency.getOrDefault(currentVertex, new ArrayList<>());
                for (int neighbor : neighbors) {
                    // If neighbor has not been visited yet, enqueue it and mark it as visited
                    if (!visited.contains(neighbor)) {
                        queue.add(neighbor);
                        visited.add(neighbor);
                    }
                }
            }

            return false;
        }

        public static void main(String[] args) {
            Solution sol = new Solution();

            int[][][] edgeSets = {
                    {{0, 1}, {1, 2}, {2, 3}},
                    {{0, 1}, {0, 2}, {3, 4}},
                    {{0, 1}, {1, 2}, {2, 3}, {3, 4}},
                    {},
                    {{0, 1}, {2, 3}, {1, 3}}
            };

            int[] nValues =      {4, 5, 5, 1, 4};
            int[] sources =      {0, 0, 0, 0, 0};
            int[] destinations = {3, 4, 4, 0, 3};

            int y = 1;
            for (int t = 0; t < edgeSets.length; t++) {
                boolean result = sol.validPath(nValues[t], edgeSets[t], sources[t], destinations[t]);
                System.out.println(y++ + ".\tn: " + nValues[t]);
                System.out.println("\tEdges: " + Arrays.deepToString(edgeSets[t]));
                System.out.println("\tSource: " + sources[t] + ", Destination: " + destinations[t]);
                System.out.println("\tResult: " + result);
                System.out.println("-".repeat(100));
            }
        }
    }
}