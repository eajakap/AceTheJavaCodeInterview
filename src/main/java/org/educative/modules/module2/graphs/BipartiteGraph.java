package org.educative.modules.module2.graphs;

import java.util.Arrays;

import java.util.*;

/**
 * Problem: Determine if a given undirected graph is bipartite.
 * A graph is bipartite if we can split its set of nodes into two independent subsets
 * A and B such that every edge connects a node in A to a node in B. Equivalently, a graph is bipartite
 * if it can be colored using two colors such that no two adjacent nodes have the same color.
 * Steps:
 * 1. Initialize a color array to keep track of the color assigned to each node (0 = uncolored, 1 = color A, -1 = color B).
 * 2. Iterate over all nodes in the graph to handle disconnected components.
 * 3. For each uncolored node, perform a BFS (or DFS) to color the graph:
 *    - Assign the starting node a color (e.g., color A).
 *    - For each neighbor of the current node, check if it is uncolored:
 *      - If uncolored, assign it the opposite color and add it to the queue for further processing.
 *      - If already colored and has the same color as the current node, the graph is not bipartite; return false.
 * 4. If all nodes are colored without conflicts, return true, indicating the graph is bipartite.
 * Constraints:
 * - The graph is represented as an adjacency list, where graph[i] contains the neighbors of node i.
 * - The graph may be disconnected, so we need to check each component.
 * Time complexity: O(V + E), where V is the number of vertices and E is the number of edges, since we visit each node and edge once.
 * Space complexity: O(V), for the color array and the queue used in BFS.
 */
public class BipartiteGraph {

    /**
     * Determines if the given undirected graph is bipartite.
     *
     * A graph is bipartite if we can split its set of nodes into two independent subsets A and B
     * such that every edge connects a node in A to a node in B. Equivalently, a graph is bipartite
     * if it can be colored using two colors such that no two adjacent nodes have the same color.
     *
     * @param graph an adjacency list representation of the graph, where graph[i] contains the neighbors of node i
     * @return true if the graph is bipartite, false otherwise
     */
    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        // Color array: 0 = uncolored, 1 = color A, -1 = color B
        int[] color = new int[n];

        // Iterate over all nodes to handle disconnected components
        for (int start = 0; start < n; start++) {
            if (color[start] != 0) {
                continue;
            }
            // BFS from this unvisited node
            Queue<Integer> queue = new LinkedList<>();
            queue.add(start);
            color[start] = 1;  // Assign color A to start node

            while (!queue.isEmpty()) {
                int node = queue.poll();
                for (int neighbor : graph[node]) {
                    if (color[neighbor] == 0) {
                        // Assign opposite color to the neighbor
                        color[neighbor] = -color[node];
                        queue.add(neighbor);
                    } else if (color[neighbor] == color[node]) {
                        // Same color on both ends of an edge means not bipartite
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        BipartiteGraph sol = new BipartiteGraph();

        int[][][] testCases = {
                {{}},
                {{1}, {0}},
                {{1, 2}, {0, 2}, {0, 1}},
                {{1, 3}, {0, 2}, {1, 3}, {0, 2}},
                {{1, 2, 3}, {0}, {0}, {0}},
                {{1, 4}, {0, 2}, {1, 3}, {2, 4}, {3, 0}}
        };

        String[] descriptions = {
                "Single node, no edges -> trivially bipartite",
                "Two nodes connected -> bipartite",
                "Triangle (3-cycle) -> not bipartite",
                "Square (4-cycle) -> bipartite",
                "Star graph -> bipartite",
                "5-cycle (odd cycle) -> not bipartite"
        };

        int y = 1;
        for (int t = 0; t < testCases.length; t++) {
            System.out.println(y++ + ".\tInput array: " + Arrays.deepToString(testCases[t]));
            System.out.println("\tDescription: " + descriptions[t]);
            boolean result = sol.isBipartite(testCases[t]);
            System.out.println("\tResult: " + result);
            System.out.println("-".repeat(100));
        }
    }
}

