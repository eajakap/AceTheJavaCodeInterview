package org.educative.modules.module3.algorithms.graphs;

import java.util.*;

/**
 * Breadth-first traversal of a directed graph stored as adjacency lists.
 * <p>
 * Contains three nested helpers: {@link DoublyLinkedList} (the adjacency list),
 * {@link Graph} (the graph itself) and {@link CheckBFS} (the traversal and driver).
 */
public class GraphTraversalSolution {

    /**
     * Generic doubly linked list with head and tail pointers, used as a vertex's adjacency list.
     *
     * @param <T> type of the stored values
     */
    static class DoublyLinkedList<T> {
        /** A list node holding a value and links to its neighbours. */
        private class Node {
            private T data;
            private Node nextNode;
            private Node prevNode;
        }

        private Node headNode;
        private Node tailNode;
        private int size;

        /** Creates an empty list. */
        public DoublyLinkedList() {
            this.headNode = null;
            this.tailNode = null;
        }

        /** @return {@code true} if the list has no nodes */
        public boolean isEmpty() {
            if (headNode == null && tailNode == null)
                return true;
            return false;
        }

        public int size() {
            return size;
        }

        /** @return the first node, or {@code null} if the list is empty */
        public Node getHeadNode() {
            return headNode;
        }

        /** @return the last node, or {@code null} if the list is empty */
        public Node getTailNode() {
            return tailNode;
        }

        /** @return the number of nodes in the list */
        public int getSize() {
            return size;
        }

        /**
         * Inserts a value at the front of the list.
         *
         * @param data value to insert
         */
        public void insertAtHead(T data) {
            Node newNode = new Node();
            newNode.data = data;
            newNode.nextNode = this.headNode; //Linking newNode to head's nextNode
            newNode.prevNode = null;
            if (headNode != null)
                headNode.prevNode = newNode;
            else
                tailNode = newNode;
            this.headNode = newNode;
            size++;
        }

        /**
         * Inserts a value at the back of the list.
         *
         * @param data value to insert
         */
        public void insertAtEnd(T data) {
            //An empty list behaves the same as inserting at the head
            if(isEmpty()) {
                insertAtHead(data);
                return;
            }
            Node newNode = new Node();
            newNode.data = data;
            newNode.nextNode = null;
            newNode.prevNode = tailNode;
            tailNode.nextNode = newNode;
            tailNode = newNode;
            size++;
        }

        /**
         * Appends a value to the list; keeps neighbours in insertion order.
         *
         * @param destination value to append
         */
        public void add(T destination) {
            insertAtEnd(destination);
        }

        /** Prints the list front to back, e.g. {@code List : null <- 1 <-> 2 -> null}. */
        public void printList() {
            if (isEmpty()) {
                System.out.println("List is Empty!");
                return;
            }

            Node temp = headNode;
            System.out.print("List : null <- ");

            while (temp.nextNode != null) {
                System.out.print(temp.data.toString() + " <-> ");
                temp = temp.nextNode;
            }

            System.out.println(temp.data.toString() + " -> null");
        }

        /** Prints the list back to front by walking the {@code prevNode} links. */
        public void printListReverse() {
            if (isEmpty()) {
                System.out.println("List is Empty!");
            }

            Node temp = tailNode;
            System.out.print("List : null <- ");

            while (temp.prevNode != null) {
                System.out.print(temp.data.toString() + " <-> ");
                temp = temp.prevNode;
            }

            System.out.println(temp.data.toString() + " -> null");
        }

        /** Removes the first node; does nothing if the list is empty. */
        public void deleteAtHead() {
            if (isEmpty())
                return;

            headNode = headNode.nextNode;
            if (headNode == null)
                tailNode = null;
            else
                headNode.prevNode = null;
            size--;
        }

        /** Removes the last node; does nothing if the list is empty. */
        public void deleteAtTail() {
            if (isEmpty())
                return;
            tailNode = tailNode.prevNode;
            if (tailNode == null)
                headNode = null;
            else
                tailNode.nextNode = null;
            size--;
        }
    }
    
    /** Directed graph whose vertices are numbered {@code 0..vertices-1}, stored as adjacency lists. */
    static class Graph {
        private int vertices; //number of vertices
        private DoublyLinkedList <Integer> adjacencyList[]; //Adjacency Lists

        /**
         * Creates a graph with no edges.
         *
         * @param vertices number of vertices
         */
        @SuppressWarnings("unchecked")
        public Graph(int vertices) {
            this.vertices = vertices;
            adjacencyList = new DoublyLinkedList[vertices];
            for (int i = 0; i < vertices; ++i)
                adjacencyList[i] = new DoublyLinkedList();
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
        public DoublyLinkedList < Integer > [] getAdjacencyList() {
            return this.adjacencyList;
        }

        /** Prints every vertex followed by the vertices it has edges to. */
        public void printGraph() {
            for (int i = 0; i < this.vertices; i++) {
                System.out.print("Adjacency list of vertex " + i + ": head");
                DoublyLinkedList<Integer> adjacencyList = this.adjacencyList[i];
                DoublyLinkedList<Integer>.Node temp = adjacencyList.getHeadNode();
                while (temp != null) {
                    System.out.print(" -> " + temp.data);
                    temp = temp.nextNode;
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

    /** Breadth-first traversal over a {@link Graph}, plus a driver. */
    static class CheckBFS {
        /**
         * Traverses the whole graph breadth first. Each unvisited vertex starts a new
         * traversal, so disconnected components are covered too.
         *
         * @param g graph to traverse
         * @return vertices concatenated in visit order, e.g. {@code "01234"}
         */
        public static String bfs(Graph g){

            String result = "";

            //Checking if the graph has no vertices
            if (g.vertices < 1){
                return result;
            }

            //Boolean Array to hold the history of visited nodes (by default-false)
            boolean[] visited = new boolean[g.vertices];

            for(int i=0;i<g.vertices;i++)
            {
                //Checking whether the node is visited or not
                if(!visited[i])
                {
                    result = result + bfsVisit(g, i, visited);
                }
            }
            return result;
        }

        /**
         * Breadth-first traversal of the component reachable from {@code source},
         * using a queue. Vertices are marked visited when enqueued so none is queued twice.
         *
         * @param g       graph to traverse
         * @param source  vertex to start from
         * @param visited shared visited flags, updated in place
         * @return vertices reached from {@code source}, concatenated in visit order
         */
        public static String bfsVisit(Graph g, int source, boolean[] visited) {

            String result = "";

            //Create Queue for Breadth First Traversal and enqueue source in it
            Queue<Integer> queue = new ArrayDeque<>(g.vertices);

            queue.offer(source);;
            visited[source] = true;

            //Traverse while queue is not empty
            while (!queue.isEmpty()) {

                //Dequeue a vertex/node from queue and add it to result
                int current_node = queue.poll();

                result += String.valueOf(current_node);

                //Get adjacent vertices to the current_node from the array,
                //and if they are not already visited then enqueue them in the Queue
                DoublyLinkedList<Integer>.Node temp = null;
                if(g.adjacencyList[current_node] != null)
                    temp = g.adjacencyList[current_node].headNode;

                while (temp != null) {

                    if (!visited[temp.data]) {
                        queue.offer(temp.data);
                        visited[temp.data] = true; //Visit the current Node
                    }
                    temp = temp.nextNode;
                }
            }//end of while
            return result;
        }

        /** Driver that runs BFS on two sample graphs. */
        public static void main(String args[]) {
            Graph g = new Graph(5);
            g.addEdge(0,1);
            g.addEdge(0,2);
            g.addEdge(1,3);
            g.addEdge(1,4);
            System.out.println("Graph1:");
            g.printGraph();
            System.out.println("BFS traversal of Graph1 : " + bfs(g));
            System.out.println();

            Graph g2 = new Graph(5);
            g2.addEdge(0,1);
            g2.addEdge(0,4);
            g2.addEdge(1,2);
            g2.addEdge(3,4);
            System.out.println("Graph2:");
            g2.printGraph();
            System.out.println("BFS traversal of Graph2 : " + bfs(g2));
        }
    }

    static class CheckDFS {
        public static String dfs(Graph g){
            String result = "";
            //Checking if the graph has no vertices
            if (g.vertices < 1){
                return result;
            }

            //Boolean Array to hold the history of visited nodes (by default-false)
            boolean[] visited = new boolean[g.vertices];

            for(int i=0; i<g.vertices; i++)
            {
                //Checking whether the node is visited or not
                if(!visited[i])
                {
                    result = result + dfsVisit(g, i, visited);
                }
            }
            return result;
        }

        public static String dfsVisit(Graph g, int source, boolean[] visited) {

            String result = "";


            //Create Stack (ArrayDeque used as a stack) for Depth First Traversal and Push source in it
            Deque<Integer> stack = new ArrayDeque<>(g.vertices);

            stack.push(source);

            //Traverse while stack is not empty
            while (!stack.isEmpty()) {

                //Pop a vertex/node from stack and add it to the result
                int current_node = stack.pop();
                //A vertex can be pushed more than once; skip it if already visited
                if (visited[current_node])
                    continue;
                result += String.valueOf(current_node);

                //Get adjacent vertices to the current_node from the array,
                //and if they are not already visited then push them in the stack
                DoublyLinkedList<Integer>.Node temp = null;
                if(g.adjacencyList[current_node] !=null)
                    temp = g.adjacencyList[current_node].headNode;

                while (temp != null) {

                    if (!visited[temp.data]) {
                        stack.push(temp.data);

                    }
                    temp = temp.nextNode;
                }
                //Visit the node
                visited[current_node] = true;
            }//end of while
            return result;
        }

        public static void main(String args[]) {
            Graph g = new Graph(5);
            g.addEdge(0,1);
            g.addEdge(0,2);
            g.addEdge(1,3);
            g.addEdge(1,4);
            System.out.println("Graph1:");
            g.printGraph();
            System.out.println("DFS traversal of Graph1 : " + dfs(g));
            System.out.println();

            Graph g2 = new Graph(5);
            g2.addEdge(0,1);
            g2.addEdge(0,4);
            g2.addEdge(1,2);
            g2.addEdge(4,3);
            System.out.println("Graph2:");
            g2.printGraph();
            System.out.println("DFS traversal of Graph2 : " + dfs(g2));
        }
    }

    /** Entry point; delegates to {@link CheckBFS#main(String[])}. */
    public static void main(String[] args) {
        CheckBFS.main(args);
        CheckDFS.main(args);
    }
}
