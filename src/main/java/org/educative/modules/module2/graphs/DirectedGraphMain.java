package org.educative.modules.module2.graphs;

import java.util.ArrayList;
import java.util.List;


public class DirectedGraphMain {
    
    static class Node {
        String value;
        List<Node> neighbors;

        public Node(String value) {
            this.value = value;              // Store the value (label) of the Node
            this.neighbors = new ArrayList<>(); // List to store adjacent (connected) Nodes
        }

        // Method to add a directed edge from this Node to another Node
        public void addNeighbor(Node Node) {
            neighbors.add(Node);
        }
    }

    static class DirectedGraph {
        List<Node> Nodes;

        public DirectedGraph() {
            this.Nodes = new ArrayList<>();  // List to store all Nodes in the graph
        }

        // Method to add a Node to the graph
        public void addNode(Node Node) {
            // Avoid adding duplicate Nodes
            if (!Nodes.contains(Node)) {
                Nodes.add(Node);
            }
        }

        // Method to add a directed edge from one Node to another
        public void addEdge(Node fromNode, Node toNode) {
            // Ensure both Nodes exist in the graph before connecting
            if (Nodes.contains(fromNode) && Nodes.contains(toNode)) {
                fromNode.addNeighbor(toNode);
            }
        }
    }

    public static void main(String[] args) {
        // Create Nodes
        Node a = new Node("A");
        Node b = new Node("B");
        Node c = new Node("C");

        // Create a directed graph instance
        DirectedGraph directedGraph = new DirectedGraph();

        // Add Nodes to the graph
        directedGraph.addNode(a);
        directedGraph.addNode(b);
        directedGraph.addNode(c);

        // Add directed edges between Nodes
        directedGraph.addEdge(a, b);  // A -> B
        directedGraph.addEdge(a, c);  // A -> C
        directedGraph.addEdge(c, b);  // C -> B

        // Traverse the graph and print edges
        for (Node Node : directedGraph.Nodes) {
            for (Node neighbor : Node.neighbors) {
                System.out.println(Node.value + " -> " + neighbor.value);  // Print each directed edge
            }
        }
    }
}