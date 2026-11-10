package org.educative.modules.module2.graphs;

import java.util.Arrays;
import java.util.*;

/**
 * Problem: Path Exists in Undirected Graph
 *
 * Given an undirected graph represented as an edge list, determine if there is a path between two given vertices.
 *
 * Example 1:
 * Input: n = 4, edges = [[0,1],[1,2],[2,3]], source = 0, destination = 3
 * Output: true
 * Explanation: There is a path from vertex 0 to vertex 3 through vertices 1 and 2.
 *
 * Example 2:
 * Input: n = 5, edges = [[0,1],[0,2],[3,4]], source = 0, destination = 4
 * Output: false
 * Explanation: There is no path from vertex 0 to vertex 4.
 *
 * Constraints:
 * - The graph is undirected and may contain cycles.
 * - The number of vertices n is in the range [1, 10^5].
 * - The number of edges is in the range [0, 10^5].
 */
public class PathExistsinUnidirectedGraph {
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
        PathExistsinUnidirectedGraph sol = new PathExistsinUnidirectedGraph();

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