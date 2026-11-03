package org.educative.modules.module3.algorithms.graphs;

import java.util.LinkedList;

/**
 * Counts the distinct paths between two vertices of a directed graph using recursive
 * depth-first search.
 * <p>
 * Contains two nested helpers: {@link Graph} (adjacency-list graph) and
 * {@link Count} (the algorithm and driver).
 */
public class CountPathsBetweenNodes {
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

    /** Path counting over a {@link Graph}, plus a driver. */
    static class Count {
        /**
         * Counts the distinct paths from {@code start} to {@code destination}.
         * Assumes the graph is acyclic; a cycle on a path to the destination
         * would cause infinite recursion.
         *
         * @param g           graph to search
         * @param start       vertex to start from
         * @param destination vertex to reach
         * @return number of distinct paths
         */
        public static int countPaths(Graph g, int start, int destination) {
            int count = 0;
            return countPathsRecursive(g, start, destination, count);
        }

        /**
         * Depth-first helper that follows every outgoing edge, threading the running
         * path count through each recursive call.
         *
         * @param g           graph to search
         * @param start       current vertex
         * @param destination vertex to reach
         * @param count       paths found so far
         * @return {@code count} plus the paths found from {@code start}
         */
        public static int countPathsRecursive(Graph g, int start, int destination, int count) {

            LinkedList<Integer> Llist[];
            Llist = g.getAdjacencyList();
            //Reaching the destination completes exactly one path
            if (start == destination) {
                count++;
            } else {
                //Otherwise explore every adjacent vertex
                for (int i = 0; i < Llist[start].size(); i++) {
                    int ajdacentVertex = Llist[start].get(i);
                    count = countPathsRecursive(g, ajdacentVertex, destination, count);
                }
            }
            return count;
        }

        /**
         * Prints every path from {@code start} to {@code destination}, one per line,
         * and returns how many were found. Same acyclic assumption as {@link #countPaths}.
         *
         * @param g           graph to search
         * @param start       vertex to start from
         * @param destination vertex to reach
         * @return number of paths printed
         */
        public static int printPaths(Graph g, int start, int destination) {
            LinkedList<Integer> path = new LinkedList<>();
            path.add(start);
            return printPathsRecursive(g, start, destination, path);
        }

        /**
         * Depth-first helper that follows every outgoing edge, threading the running
         * path through each recursive call.
         *
         * @param g           graph to search
         * @param current     current vertex
         * @param destination vertex to reach
         * @param path        vertices visited so far, in order
         * @return number of paths printed from {@code current}
         */
        private static int printPathsRecursive(Graph g, int current, int destination, LinkedList<Integer> path) {
            if (current == destination) {
                System.out.println(path);
                return 1;
            }
            int count = 0;
            for (int next : g.getAdjacencyList()[current]) {
                path.addLast(next);   //choose
                count += printPathsRecursive(g, next, destination, path);
                path.removeLast();    //backtrack so siblings see the original path
            }
            return count;
        }

        /** Driver that prints the path counts for two sample graphs. */
        public static void main(String args[]) {

            int answer;
            Graph g1 = new Graph(6);
            g1.addEdge(0, 1);
            g1.addEdge(0, 2);
            g1.addEdge(1, 2);
            g1.addEdge(1, 3);
            g1.addEdge(3, 4);
            g1.addEdge(2, 3);
            g1.addEdge(4, 5);

            answer = Count.countPaths(g1, 0, 5);
            System.out.println(answer);
            Count.printPaths(g1, 0, 5);

            Graph g2 = new Graph(7);
            g2.addEdge(0, 1);
            g2.addEdge(1, 2);
            g2.addEdge(1, 5);
            g2.addEdge(2, 3);
            g2.addEdge(2, 4);
            g2.addEdge(5, 3);
            g2.addEdge(5, 6);
            g2.addEdge(3, 6);

            answer = Count.countPaths(g2, 0, 3);
            System.out.println(answer);
            Count.printPaths(g2, 0, 3);

        }
    }

    /** Entry point; delegates to {@link Count#main(String[])}. */
    public static void main(String[] args) {
        Count.main(args);
    }
}
