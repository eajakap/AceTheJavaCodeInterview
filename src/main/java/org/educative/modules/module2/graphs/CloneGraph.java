package org.educative.modules.module2.graphs;

import java.util.*;

public class CloneGraph {

    static class Node {
        int data;
        List<Node> neighbors;

        public Node(int data) {
            this.data = data;                 // Store the Node's value or label
            this.neighbors = new ArrayList<>();  // Store connected neighboring Nodes
        }

        // Add a neighboring Node to this Node's neighbor list
        public void addNeighbor(Node neighbor) {
            neighbors.add(neighbor);
        }
    }

    public static Node clone(Node node) {
            /*
             * Clone a connected undirected graph using BFS.
             * @param node: Node
             * @return: Node
             */

            // Handle empty graph case
            if (node == null) {
                return null;
            }

            // HashMap to map original node -> cloned node
            Map<Node, Node> cloneMap = new HashMap<>();
            cloneMap.put(node, new Node(node.data));

            // BFS queue starting from the given node
            Queue<Node> queue = new LinkedList<>();
            queue.add(node);

            while (!queue.isEmpty()) {
                // Process current node
                Node currentNode = queue.poll();

                // Iterate through all neighbors of current node
                for (Node neighbor : currentNode.neighbors) {
                    // If neighbor hasn't been cloned yet, clone it and add to queue
                    if (!cloneMap.containsKey(neighbor)) {
                        cloneMap.put(neighbor, new Node(neighbor.data));
                        queue.add(neighbor);
                    }
                    // Link the cloned current node to the cloned neighbor
                    cloneMap.get(currentNode).neighbors.add(cloneMap.get(neighbor));
                }
            }

            // Return the clone of the starting node
            return cloneMap.get(node);
        }

    public class GraphUtility {

        /**
         * Creates a graph from an adjacency list.
         * testCase[i] contains neighbors of node (i+1)
         *
         * Example:
         * {{2,3}, {1,3}, {1,2}}
         * means:
         * 1 -> 2,3
         * 2 -> 1,3
         * 3 -> 1,2
         */
        public static Node createGraph(int[][] adjList) {

            // Create all nodes first
            Map<Integer, Node> map = new HashMap<>();
            for (int i = 0; i < adjList.length; i++) {
                map.put(i + 1, new Node(i + 1));
            }

            // Add neighbors
            for (int i = 0; i < adjList.length; i++) {
                CloneGraph.Node node = map.get(i + 1);
                for (int neighbor : adjList[i]) {
                    node.addNeighbor(map.get(neighbor));
                }
            }

            // Return node 1 as the starting point
            return map.get(1);
        }

        /**
         * Converts a graph into a 2D adjacency list representation.
         * BFS traversal ensures we cover all reachable nodes.
         */
        public static List<List<Integer>> create2DList(Node start) {
            List<List<Integer>> result = new ArrayList<>();
            Map<Node, Integer> indexMap = new HashMap<>();
            List<Node> nodes = new ArrayList<>();

            Queue<Node> queue = new ArrayDeque<>();
            Set<CloneGraph.Node> visited = new HashSet<>();

            queue.add(start);
            visited.add(start);

            while (!queue.isEmpty()) {
                CloneGraph.Node curr = queue.poll();
                nodes.add(curr);

                for (CloneGraph.Node nbr : curr.neighbors) {
                    if (!visited.contains(nbr)) {
                        visited.add(nbr);
                        queue.add(nbr);
                    }
                }
            }

            // Assign indices based on BFS order
            for (int i = 0; i < nodes.size(); i++) {
                indexMap.put(nodes.get(i), i);
            }

            // Build adjacency list
            for (CloneGraph.Node node : nodes) {
                List<Integer> neighbors = new ArrayList<>();
                for (CloneGraph.Node nbr : node.neighbors) {
                    neighbors.add(nbr.data);
                }
                result.add(neighbors);
            }

            return result;
        }
    }

    public static void main(String[] args) {
        int[][][] testCases = {
                {{2, 3}, {1, 3}, {1, 2}},
                {{2}, {1, 3}, {2}},
                {{2, 3, 4, 5}, {1}, {1}, {1}, {1}},
                {{2}, {1}},
                {{2, 5}, {1, 3}, {2, 4}, {3, 5}, {4, 1}}
        };

        String[] descriptions = {
                "Triangle graph (3 nodes, each connected to the other two)",
                "Linear chain of 3 nodes: 1-2-3",
                "Star graph with center node 1 connected to 2,3,4,5",
                "Two nodes connected to each other",
                "5-node cycle: 1-2-3-4-5-1"
        };

        int y = 1;
        for (int t = 0; t < testCases.length; t++) {
            Node originalNode = GraphUtility.createGraph(testCases[t]);
            Node clonedNode = CloneGraph.clone(originalNode);
            List<List<Integer>> resultAdjList = GraphUtility.create2DList(clonedNode);
            boolean isDeepCopy = (clonedNode != originalNode);

            System.out.println(y++ + ".\tInput adjacency list: " + Arrays.deepToString(testCases[t]));
            System.out.println("\tResult: " + resultAdjList);
            System.out.println("\tIs deep copy: " + isDeepCopy);
            System.out.println("-".repeat(100));
        }
    }

}
