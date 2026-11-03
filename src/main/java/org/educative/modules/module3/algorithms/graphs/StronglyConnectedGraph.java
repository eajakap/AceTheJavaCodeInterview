package org.educative.modules.module3.algorithms.graphs;

import java.util.Iterator;
import java.util.LinkedList;
import java.io.*;
import java.util.*;


/**
 * Checks whether a directed graph is strongly connected, i.e. there is a path between
 * every ordered pair of vertices.
 * <p>
 * Approach: every vertex must be reachable from vertex 0 in the graph, and (via the
 * transpose) vertex 0 must be reachable from every vertex. Two DFS passes, O(V + E).
 * <p>
 * Contains two nested helpers: {@link Graph} (adjacency-list graph) and
 * {@link StronglyConnected} (the algorithm and driver).
 */
public class StronglyConnectedGraph {

    /** Directed graph whose vertices are numbered {@code 0..vertices-1}, stored as adjacency lists. */
    static class Graph {
        private int vertices; //number of vertices
        private LinkedList < Integer > adjacencyList[]; //Adjacency Lists

        /**
         * Creates a graph with no edges.
         *
         * @param vert number of vertices
         */
        @SuppressWarnings("unchecked")
        Graph(int vert) {
            this.vertices = vert;
            this.adjacencyList = new LinkedList[vertices];
            for (int i = 0; i < this.vertices; ++i) {
                this.adjacencyList[i] = new LinkedList();
            }
        }

        /**
         * Adds a directed edge.
         *
         * @param source      vertex the edge starts from
         * @param destination vertex the edge points to
         */
        void addEdge(int source, int destination) {
            this.adjacencyList[source].add(destination);
        }

        /** @return the number of vertices */
        public int getVertices() {
            return this.vertices;
        }

        /** @return the adjacency lists, indexed by vertex */
        public LinkedList< Integer >[] getAdjacencyList() {
            return this.adjacencyList;
        }

        /**
         * Builds the transpose of this graph (every edge reversed). This graph is unchanged.
         *
         * @return a new graph with each edge u -> v replaced by v -> u
         */
        public Graph getTranspose() {
            Graph g = new Graph(vertices);
            for (int j = 0; j < vertices; j++) {
                Iterator< Integer > i = adjacencyList[j].listIterator();
                //For each edge j -> x, add x -> j to the transpose
                while (i.hasNext())
                    g.adjacencyList[i.next()].add(j);
            }
            return g;
        }

        /** Prints every vertex followed by the vertices it has edges to. */
        public void printGraph() {
            for (int v = 0; v < vertices; v++) {
                System.out.print("Adjacency list of vertex " + v + ": ");
                for (Integer neighbor : adjacencyList[v]) {
                    System.out.print(neighbor + " ");
                }
                System.out.println();
            }
        }
    }

    /** Strong-connectivity check over a {@link Graph}, plus a driver. */
    static class StronglyConnected {
        /**
         * Recursive DFS that marks every vertex reachable from {@code v}.
         *
         * @param g       graph to traverse
         * @param v       vertex to start from
         * @param visited visited flags, updated in place
         */
        public static void utilityFunction(Graph g, int v, boolean visited[]) {

            visited[v] = true;
            int temp;
            LinkedList < Integer > Llist[];
            Llist = g.getAdjacencyList();
            Iterator < Integer > i = Llist[v].iterator();
            //Recurse into every neighbour not yet visited
            while (i.hasNext()) {
                temp = i.next();
                if (!visited[temp])
                    utilityFunction(g, temp, visited);
            }
        }

        /**
         * Tests whether the graph is strongly connected.
         *
         * @param g graph to test
         * @return {@code Boolean.TRUE} if strongly connected, otherwise {@code Boolean.FALSE}
         */
        public static Object isStronglyConnected(Graph g) {
            int numofVertices = g.getVertices();
            boolean visited[] = new boolean[numofVertices];

            //Pass 1: DFS from vertex 0 on the original graph
            utilityFunction(g, 0, visited);
            //Any unvisited vertex is unreachable from 0, so not strongly connected
            for (int i = 0; i < numofVertices; i++)
                if (visited[i] == false)
                    return false;

            //Pass 2: DFS from vertex 0 on the transpose, which checks that every vertex can reach 0
            Graph g1 = g.getTranspose();

            for (int i = 0; i < numofVertices; i++)
                visited[i] = false;

            utilityFunction(g1, 0, visited);

            for (int i = 0; i < numofVertices; i++)
                if (visited[i] == false)
                    return false;

            return true;
        }

        /** Driver that tests one strongly connected and one non-strongly-connected sample graph. */
        public static void main(String[] args) {
            Graph g1 = new Graph(5);
            g1.addEdge(0, 1);
            g1.addEdge(1, 2);
            g1.addEdge(2, 3);
            g1.addEdge(3, 0);
            g1.addEdge(2, 4);
            g1.addEdge(4, 2);

            System.out.println("Graph:");
            g1.printGraph();

            if ((boolean) isStronglyConnected(g1))
                System.out.println("The graph g1 is strongly connected.");
            else
                System.out.println("The graph g1 is not strongly connected.");

            Graph g2 = new Graph(4);
            g2.addEdge(0, 1);
            g2.addEdge(1, 2);
            g2.addEdge(2, 3);
            g2.printGraph();

            if ((boolean) isStronglyConnected(g2))
                System.out.println("The graph g1 is strongly connected.");
            else
                System.out.println("The graph g2 is not strongly connected.");
        }
    }

    /** Entry point; delegates to {@link StronglyConnected#main(String[])}. */
    public static void main(String[] args) {
        StronglyConnected.main(args);
    }
}
