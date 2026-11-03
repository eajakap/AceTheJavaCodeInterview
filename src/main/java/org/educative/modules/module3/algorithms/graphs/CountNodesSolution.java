package org.educative.modules.module3.algorithms.graphs;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Counts the nodes at a given level (distance from a source, where the source is level 1)
 * of a directed graph using breadth-first search.
 * <p>
 * Contains two nested helpers: {@link Graph} (adjacency-list graph) and
 * {@link CountNodes} (the algorithm and driver).
 */
public class CountNodesSolution {
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

    /** BFS-based level counting over a {@link Graph}, plus a driver. */
    static class CountNodes {
        /**
         * Counts vertices at the given level when traversing breadth first from {@code source}.
         * The source itself is level 1, its neighbours level 2, and so on. Unreachable
         * vertices are never counted.
         *
         * @param g      graph to traverse
         * @param source vertex to start from (level 1)
         * @param level  level to count
         * @return number of vertices at that level
         */
        public static int numberOfNodesInGivenLevel(Graph g, int source, int level) {
            int count = 0;
            int num_of_vertices = g.getVertices();
            //visited[v] == 0 means unvisited; otherwise it stores the level of v (source = 1).
            //A node is marked as soon as it is added to the queue
            int visited[] = new int[num_of_vertices];
            //Create a linkedlist array called Queue
            LinkedList < Integer > queue = new LinkedList < Integer > ();
            //The source is level 1; mark it and enqueue it
            visited[source] = 1;
            queue.add(source);
            //Traverse while queue is not empty
            while (queue.size() != 0) {
                //Dequeue the next vertex (source is reused as the current vertex)
                source = queue.poll();
                //Get adjacent vertices to the current node from the list
                LinkedList < Integer > Llist[];
                Llist = g.getAdjacencyList();
                Iterator< Integer > i = Llist[source].listIterator();

                while (i.hasNext()) {
                    int temp = i.next();
                    //If not already visited, its level is one more than the current vertex's
                    if (visited[temp] != 1) {
                        visited[temp] = visited[source] + 1;
                        queue.add(temp);
                    }
                }
            }
            //calculating the count for the level
            for (int i = 0; i < num_of_vertices; i++)
                if (visited[i] == level)
                    count++;
            return count;
        }

        /** Driver that prints the node count for levels 1 to 4 of a sample graph. */
        public static void main(String args[]) {
            Graph g = new Graph(6);
            g.addEdge(0, 1);
            g.addEdge(0, 2);
            g.addEdge(1, 3);
            g.addEdge(2, 3);
            g.addEdge(3, 5);
            g.addEdge(2, 4);

            int answer;

            answer = CountNodes.numberOfNodesInGivenLevel(g, 0, 1);
            System.out.println(answer);
            answer = CountNodes.numberOfNodesInGivenLevel(g, 0, 2);
            System.out.println(answer);
            answer = CountNodes.numberOfNodesInGivenLevel(g, 0, 3);
            System.out.println(answer);
            answer = CountNodes.numberOfNodesInGivenLevel(g, 0, 4);
            System.out.println(answer);
        }

    }

    /** Entry point; delegates to {@link CountNodes#main(String[])}. */
    public static void main(String args[]) {
        CountNodes.main(args);
    }
}
