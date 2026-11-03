package org.educative.modules.module3.algorithms.graphs;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Determines whether removing an edge from an undirected graph splits it into separate
 * components (i.e. whether the edge is a bridge).
 * <p>
 * The edge is removed from both endpoints' adjacency lists, then a DFS from vertex 0
 * checks that every vertex is still reachable. The removal is permanent: the graph is
 * not restored afterwards.
 * <p>
 * Contains {@link Graph} (undirected adjacency-list graph, an inner class) and
 * {@link Connected} (the algorithm).
 */
public class RemovalEdgeSolution {
    /** Undirected graph whose vertices are numbered {@code 0..vertices-1}, stored as adjacency lists. */
    class Graph {
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
        public LinkedList < Integer > [] getAdjacencyList() {
            return this.adjacencyList;
        }

        /**
         * Recursive DFS that marks every vertex reachable from {@code v}.
         *
         * @param v       vertex to start from
         * @param visited visited flags, updated in place
         */
        public void dfsTraversal(int v, boolean visited[]) {
            visited[v] = true;
            int source = 0;


            Iterator< Integer > i = adjacencyList[v].iterator();
            Integer temp;
            //Recurse into every neighbour not yet visited
            while (i.hasNext()) {
                temp = i.next();
                if (!visited[temp])
                    dfsTraversal(temp, visited);
            }
        }
    };

    /** Connectivity checks over a {@link Graph}. */
    static class Connected {
        /**
         * Checks whether every vertex is reachable from vertex 0.
         *
         * @param g undirected graph to test (must have at least one vertex)
         * @return {@code true} if the graph is connected
         */
        public static boolean isConnected(Graph g) {

            int numofVertices = g.getVertices();
            boolean visited[] = new boolean[numofVertices];

            g.dfsTraversal(0, visited);

            //Vertex 0 is visited by definition; any other unvisited vertex means a separate component
            for (int i = 1; i < numofVertices; i++)
                if (visited[i] == false)
                    return false;

            return true;
        }

        /**
         * Removes the edge {@code source}-{@code destination} and reports whether the graph
         * is no longer connected. The edge stays removed afterwards, and the edge must exist
         * (otherwise {@code remove(-1)} throws). Assumes the graph was connected beforehand.
         *
         * @param g           graph to modify
         * @param source      one endpoint of the edge
         * @param destination the other endpoint of the edge
         * @return {@code Boolean.TRUE} if removal disconnects the graph, otherwise {@code Boolean.FALSE}
         */
        public static Object willCauseSeparateComponents(Graph g, int source, int destination) {

            //Remove the edge from both adjacency lists. indexOf gives the position because
            //remove(int) would otherwise be ambiguous with remove(Object) for Integer
            g.getAdjacencyList()[source].remove(g.getAdjacencyList()[source].indexOf(destination));
            g.getAdjacencyList()[destination].remove(g.getAdjacencyList()[destination].indexOf(source));

            boolean result = isConnected(g);

            //Not connected after removal means the edge was a bridge
            if (result == false)
                return true;
            else
                return false;
        }
    }

    /**
     * Driver: builds a 5-vertex cycle, removes edge (3, 4) (still connected), then removes
     * (1, 2) from the already-modified graph (now disconnected).
     */
    public static void main(String[] args) {
        Graph g1 = new RemovalEdgeSolution().new Graph(5);
        g1.addEdge(0, 1);
        g1.addEdge(0, 4);
        g1.addEdge(1, 2);
        g1.addEdge(2, 3);
        g1.addEdge(3, 4);

        System.out.println("Removing edge (3, 4) will cause separate components: " + Connected.willCauseSeparateComponents(g1, 3, 4));
        System.out.println("Removing edge (1, 2) will cause separate components: " + Connected.willCauseSeparateComponents(g1, 1, 2));
    }
}
