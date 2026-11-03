package org.educative.modules.module3.algorithms.graphs;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Computes and prints the transpose of a directed graph, i.e. the same graph with
 * every edge reversed.
 * <p>
 * Contains two nested helpers: {@link Graph} (adjacency-list graph) and
 * {@link Transpose} (the algorithm and driver).
 */
public class TransposeGraphSolution {
    /** Directed graph whose vertices are numbered {@code 0..vertices-1}, stored as adjacency lists. */
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
         * Adds a directed edge.
         *
         * @param source      vertex the edge starts from
         * @param destination vertex the edge points to
         */
        void addEdge(int source, int destination) {
            adjacencyList[source].add(destination);
        }

        /** @return the number of vertices */
        public int getVertices() {
            return this.vertices;
        }

        /** @return the adjacency lists, indexed by vertex */
        public LinkedList< Integer >[] getAdjacencyList() {
            return this.adjacencyList;
        }

        /** Prints every vertex followed by the vertices it has edges to. */
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

    /** Graph transposition over a {@link Graph}, plus a driver. */
    static class Transpose {
        /**
         * Builds the transpose of {@code g} (every edge u -> v becomes v -> u) and prints it.
         * The original graph is not modified. Runs in O(V + E) apart from the
         * indexed {@code LinkedList.get}, which makes it O(V + E^2) worst case per list.
         *
         * @param g graph to transpose
         */
        public static void getTranspose(Graph g) {
            int num_of_vertices = g.getVertices();

            //Adjacency lists of the original graph
            LinkedList <Integer> Llist[] = g.getAdjacencyList();

            //New graph to store the transpose
            Graph transposedGraph = new Graph(num_of_vertices);

            //For each edge v -> w in the original, add w -> v to the transpose
            for (int v = 0; v < g.getVertices(); v++) {
                for (int i = 0; i < Llist[v].size(); i++) {
                    // Add an edge from the destination vertex to the source vertex in the transposed graph
                    transposedGraph.addEdge(Llist[v].get(i), v);
                }
            }
            // Print the transposed graph
            transposedGraph.printGraph();
        }

        /** Driver that prints the transpose of a sample graph. */
        public static void main(String args[]) {
            Graph g1 = new Graph(5);
            g1.addEdge(0, 1);
            g1.addEdge(1, 2);
            g1.addEdge(2, 3);
            g1.addEdge(3, 0);
            g1.addEdge(2, 4);
            g1.addEdge(4, 2);

            System.out.println("Original Graph: ");
            g1.printGraph();

            System.out.println("Transpose of given Graph: ");
            Transpose.getTranspose(g1);
        }
    }

    /** Entry point; delegates to {@link Transpose#main(String[])}. */
    public static void main(String[] args) {
        Transpose.main(args);
    }
}
