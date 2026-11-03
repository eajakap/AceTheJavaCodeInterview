package org.educative.modules.module3.algorithms.graphs;
import java.io.*;
import java.util.*;

/**
 * Checks whether an undirected graph is bipartite, i.e. its vertices can be split into two
 * sets so that every edge joins vertices from different sets (2-colourable).
 * <p>
 * Approach: DFS that gives each newly reached vertex the opposite colour of its parent;
 * an edge between two vertices of the same colour proves the graph is not bipartite.
 * <p>
 * Contains two nested helpers: {@link Graph} (undirected adjacency-list graph) and
 * {@link Bipartite} (the algorithm and driver).
 */
public class CheckForBipartiteGraph {

    /** Undirected graph whose vertices are numbered {@code 0..vertices-1}, stored as adjacency lists. */
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
            for (int i = 0; i < this.vertices; ++i)
                this.adjacencyList[i] = new LinkedList();
        }

        /**
         * Adds an undirected edge by recording it in both endpoints' adjacency lists.
         *
         * @param source      one endpoint of the edge
         * @param destination the other endpoint of the edge
         */
        void addEdge(int source, int destination) {
            this.adjacencyList[source].add(destination);
            //for undirected graph edge added from destination to source as well
            this.adjacencyList[destination].add(source);
        }

        /** @return the number of vertices */
        public int getVertices() {
            return this.vertices;
        }

        /** @return the adjacency lists, indexed by vertex */
        public LinkedList < Integer > [] getAdj() {
            return this.adjacencyList;
        }
    }

    /** Bipartite check over a {@link Graph}, plus a driver. */
    static class Bipartite {
        /**
         * Colours the component reachable from {@code source} and checks for a conflict.
         * The caller must mark {@code source} visited and give it a colour beforehand.
         *
         * @param g       graph to examine
         * @param source  vertex whose neighbours are explored
         * @param visited visited flags, updated in place
         * @param color   colour (true/false) of each visited vertex, updated in place
         * @return {@code Boolean.TRUE} if no colouring conflict was found, otherwise {@code Boolean.FALSE}
         */
        public static Object isBipartite(Graph g, int source, boolean visited[], boolean color[]) {

            for (int u: g.getAdj()[source]) {

                //Unvisited neighbour: give it the opposite colour and recurse
                if (visited[u] == false) {

                    visited[u] = true;

                    color[u] = !color[source];

                    //Propagate a conflict found deeper in the DFS
                    if (String.valueOf(isBipartite(g, u, visited, color)) == "false")
                        return false;
                }

                //Visited neighbour with the same colour: an odd cycle exists
                else if (color[source] == color[u]) {
                    return false;
                }
            }

            return true;
        }

        /**
         * Checks a whole graph, including disconnected ones, by starting a search from
         * every vertex not yet visited.
         *
         * @param g graph to examine
         * @return {@code true} if the graph is bipartite
         */
        public static boolean isGraphBipartite(Graph g) {
            boolean[] visited = new boolean[g.getVertices()];
            boolean[] color = new boolean[g.getVertices()];
            for (int i = 0; i < g.getVertices(); i++) {
                if (!visited[i]) {
                    visited[i] = true; //start vertex gets the default colour (false)
                    if (!(boolean) isBipartite(g, i, visited, color))
                        return false;
                }
            }
            return true;
        }

        /** Driver that tests an even cycle (bipartite) and an odd cycle (not bipartite). */
        public static void main(String[] args) {
            Graph g1 = new Graph(4);
            g1.addEdge(0, 1);
            g1.addEdge(1, 2);
            g1.addEdge(2, 3);
            g1.addEdge(3, 0);
            System.out.println("Even cycle (0-1-2-3) is bipartite: " + isGraphBipartite(g1));

            Graph g2 = new Graph(3);
            g2.addEdge(0, 1);
            g2.addEdge(1, 2);
            g2.addEdge(2, 0);
            System.out.println("Triangle (0-1-2) is bipartite: " + isGraphBipartite(g2));

            Graph g3 = new Graph(7);
            g3.addEdge(1, 2);
            g3.addEdge(2, 3);
            g3.addEdge(3, 4);
            g3.addEdge(4, 5);
            g3.addEdge(5, 6);
            g3.addEdge(6, 1);


            Graph g4 = new Graph(7);
            g4.addEdge(3, 2);
            g4.addEdge(1, 4);
            g4.addEdge(2, 1);
            g4.addEdge(5, 3);
            g4.addEdge(6, 2);
            g4.addEdge(3, 1);

            boolean[] discovered = new boolean[8];
            boolean[] color = new boolean[8];
            discovered[1] = true;
            color[1] = false;


            //Example 1
            Object x = Bipartite.isBipartite(g3, 1, discovered, color);
            System.out.println("Graph3 is bipartite: " + x);


            //Example 2
            x = Bipartite.isBipartite(g4, 1, discovered, color);
            System.out.println("Graph4 is bipartite: " + x);

        }
    }

    /** Entry point; delegates to {@link Bipartite#main(String[])}. */
    public static void main(String[] args) {
        Bipartite.main(args);
    }
}
