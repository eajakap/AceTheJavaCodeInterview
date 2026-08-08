package org.educative.modules.module2.graphs;

import java.util.ArrayList;
import java.util.List;

class DGNode {
    String value;
    List<DGNode> neighbors;

    public DGNode(String value) {
        this.value = value;              // Store the value (label) of the DGNode
        this.neighbors = new ArrayList<>(); // List to store adjacent (connected) DGNodes
    }

    // Method to add a directed edge from this DGNode to another DGNode
    public void addNeighbor(DGNode DGNode) {
        neighbors.add(DGNode);
    }
}

public class DirectedGraph {
    List<DGNode> DGNodes;

    public DirectedGraph() {
        this.DGNodes = new ArrayList<>();  // List to store all DGNodes in the graph
    }

    // Method to add a DGNode to the graph
    public void addDGNode(DGNode DGNode) {
        // Avoid adding duplicate DGNodes
        if (!DGNodes.contains(DGNode)) {
            DGNodes.add(DGNode);
        }
    }

    // Method to add a directed edge from one DGNode to another
    public void addEdge(DGNode fromDGNode, DGNode toDGNode) {
        // Ensure both DGNodes exist in the graph before connecting
        if (DGNodes.contains(fromDGNode) && DGNodes.contains(toDGNode)) {
            fromDGNode.addNeighbor(toDGNode);
        }
    }
}

class TestMain {
    public static void main(String[] args) {
        // Create DGNodes
        DGNode a = new DGNode("A");
        DGNode b = new DGNode("B");
        DGNode c = new DGNode("C");

        // Create a directed graph instance
        DirectedGraph directedGraph = new DirectedGraph();

        // Add DGNodes to the graph
        directedGraph.addDGNode(a);
        directedGraph.addDGNode(b);
        directedGraph.addDGNode(c);

        // Add directed edges between DGNodes
        directedGraph.addEdge(a, b);  // A -> B
        directedGraph.addEdge(a, c);  // A -> C
        directedGraph.addEdge(c, b);  // C -> B

        // Traverse the graph and print edges
        for (DGNode DGNode : directedGraph.DGNodes) {
            for (DGNode neighbor : DGNode.neighbors) {
                System.out.println(DGNode.value + " -> " + neighbor.value);  // Print each directed edge
            }
        }
    }
}