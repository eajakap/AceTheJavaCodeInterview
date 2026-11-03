package org.educative.modules.module3.algorithms.graphs;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class RemovalEdgeSolution {
    /** Directed graph whose vertices are numbered {@code 0..vertices-1}, stored as adjacency lists. */
    class Graph {
        private int vertices; //number of vertices
        private LinkedList < Integer > adjacencyList[]; //Adjacency Lists
        @SuppressWarnings("unchecked")
            // Constructor
        Graph(int vert) {
            this.vertices = vert;
            this.adjacencyList = new LinkedList[vertices];
            for (int i = 0; i < this.vertices; ++i)
                this.adjacencyList[i] = new LinkedList();
        }

        // Function to add an edge into the graph
        void addEdge(int source, int destination) {
            this.adjacencyList[source].add(destination);
            //for undirected graph edge added from destination to source as well
            this.adjacencyList[destination].add(source);
        }

        public int getVertices() {
            return this.vertices;
        }

        public LinkedList < Integer > [] getAdjacencyList() {
            return this.adjacencyList;
        }


        public void dfsTraversal(int v, boolean visited[]) {
            visited[v] = true;
            int source = 0;


            Iterator< Integer > i = adjacencyList[v].iterator();
            Integer temp;
            while (i.hasNext()) {
                temp = i.next();
                if (!visited[temp])
                    dfsTraversal(temp, visited);
            }
        }
    };

    static class Connected {
        public static boolean isConnected(Graph g) {

            int numofVertices = g.getVertices();
            boolean visited[] = new boolean[numofVertices];

            g.dfsTraversal(0, visited);

            for (int i = 1; i < numofVertices; i++)
                if (visited[i] == false)
                    return false;

            return true;
        }

        public static Object willCauseSeparateComponents(Graph g, int source, int destination) {

            g.getAdjacencyList()[source].remove(g.getAdjacencyList()[source].indexOf(destination));
            g.getAdjacencyList()[destination].remove(g.getAdjacencyList()[destination].indexOf(source));

            boolean result = isConnected(g);

            if (result == false)
                return true;
            else
                return false;
        }
    }

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
