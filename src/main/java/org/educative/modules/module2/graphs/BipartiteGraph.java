package org.educative.modules.module2.graphs;

import java.util.Arrays;

import java.util.*;

public class BipartiteGraph {

    static class Solution {
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
            Solution sol = new Solution();

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
                boolean result = sol.isBipartite(testCases[t]);
                System.out.println(y++ + ".\tInput array: " + Arrays.deepToString(testCases[t]));
                System.out.println("\tDescription: " + descriptions[t]);
                System.out.println("\tResult: " + result);
                System.out.println("-".repeat(100));
            }
        }
    }

    public static void main(String[] args) {
        Solution.main(args);
    }
}
