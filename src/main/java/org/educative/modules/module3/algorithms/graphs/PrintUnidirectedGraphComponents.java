package org.educative.modules.module3.algorithms.graphs;

import java.util.LinkedList;

/**
 * Prints the connected components of an undirected graph, one component per line.
 * <p>
 * Each unvisited vertex starts a recursive depth-first search that prints every vertex
 * reachable from it, which is exactly one component. Runs in O(V + E).
 * <p>
 * Contains two nested helpers: {@link Graph} (undirected adjacency-list graph) and
 * {@link PrintComp} (the algorithm and driver).
 */
public class PrintUnidirectedGraphComponents {
    /** Undirected graph whose vertices are numbered {@code 0..vertices-1}, stored as adjacency lists. */
    static class Graph {
        private int vertices; //number of vertices
        private LinkedList<Integer> adjacencyList[]; //Adjacency Lists

        /**
         * Creates a graph with no edges.
         *
         * @param vertices number of vertices
         */
        @SuppressWarnings("unchecked")
        public Graph(int vertices) {
            this.vertices = vertices;
            adjacencyList = new LinkedList[vertices];
            for (int i = 0; i < vertices; ++i)
                adjacencyList[i] = new LinkedList<>();
        }

        /**
         * Adds an undirected edge by recording it in both endpoints' adjacency lists.
         *
         * @param source      one endpoint of the edge
         * @param destination the other endpoint of the edge
         */
        void addEdge(int source, int destination) {
            //Undirected: each endpoint lists the other
            adjacencyList[source].add(destination);
            adjacencyList[destination].add(source);
        }

        /** @return the number of vertices */
        public int getVertices() {
            return this.vertices;
        }

        /** @return the adjacency lists, indexed by vertex */
        public LinkedList< Integer >[] getAdjacencyList() {
            return this.adjacencyList;
        }

        /** Prints every vertex followed by its neighbours. */
        public void printGraph() {
            for (int i = 0; i < this.vertices; i++) {
                System.out.print("Adjacency list of vertex " + i + ": head");
                LinkedList<Integer> adjacencyList = this.adjacencyList[i];
                for (Integer temp : adjacencyList) {
                    System.out.print(" -> " + temp);
                }
                System.out.println();
            }
        }

        /** Driver that builds a small graph and prints it. */
        public static void main(String[] args) {
            Graph g = new Graph(5);
            g.addEdge(0, 1);
            g.addEdge(0, 2);
            g.addEdge(1, 3);
            g.addEdge(2, 3);
            g.addEdge(3, 0);
            g.printGraph();
        }
    }

    /** Connected-component printing over a {@link Graph}, plus a driver. */
    static class PrintComp {
        /**
         * Prints each connected component on its own line.
         *
         * @param g undirected graph to examine
         */
        public static void printConnectedComponents(Graph g) {
            int num_vertices = g.getVertices();
            boolean[] visited = new boolean[num_vertices];
            for (int i = 0; i < num_vertices; ++i) {
                //An unvisited vertex belongs to a component not printed yet
                if (!visited[i]) {
                    utilityFunction(g, i, visited);
                    System.out.println();
                }
            }
        }

        /**
         * Recursive DFS that prints and marks every vertex reachable from {@code v}.
         * Recursion depth can reach the component size, so very large components may overflow the stack.
         *
         * @param g       graph to traverse
         * @param v       vertex to start from
         * @param visited visited flags, updated in place
         */
        public static void utilityFunction(Graph g, int v, boolean visited[]) {
            visited[v] = true;
            System.out.print(v + " ");
            LinkedList < Integer > Llist[];
            Llist = g.getAdjacencyList();
            //Visiting each neighbour once; the visited check stops the DFS bouncing back along the mirrored edge
            for (int i: Llist[v]) {
                if (!visited[i]) {
                    utilityFunction(g, i, visited);
                }
            }
        }

        /** Driver that prints the components of a 7-vertex graph: {0,1,2} and {3,4,5,6}. */
        public static void main(String args[]) {
            Graph g = new Graph(7);
            g.addEdge(0, 1);
            g.addEdge(1, 2);
            g.addEdge(3, 4);
            g.addEdge(5, 3);
            g.addEdge(5, 6);
            g.addEdge(3, 6);

            System.out.println("The connected components are:");
            PrintComp.printConnectedComponents(g);

        }

    }

    /** Entry point; delegates to {@link PrintComp#main(String[])}. */
    public static void main(String[] args) {
        PrintComp.main(args);
    }
}
