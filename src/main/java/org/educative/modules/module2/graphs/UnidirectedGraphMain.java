package org.educative.modules.module2.graphs;

import java.util.ArrayList;
import java.util.List;

public class UnidirectedGraphMain {

    static class UDGNode {
        String value;
        List<UDGNode> neighbors;

        public UDGNode(String value) {
            this.value = value;                 // Store the UDGNode's value or label
            this.neighbors = new ArrayList<>();  // Store connected neighboring UDGNodes
        }

        // Add a neighboring UDGNode to this UDGNode's neighbor list
        public void addNeighbor(UDGNode UDGNode) {
            neighbors.add(UDGNode);
        }
    }

    static class UndirectedGraph {
        List<UDGNode> UDGNodes;

        public UndirectedGraph() {
            this.UDGNodes = new ArrayList<>();  // Store all UDGNodes in the graph
        }

        // Add a UDGNode to the graph if it is not already present
        public void addUDGNode(UDGNode UDGNode) {
            if (!UDGNodes.contains(UDGNode)) {
                UDGNodes.add(UDGNode);
            }
        }

        // Add an undirected edge between two UDGNodes
        public void addEdge(UDGNode UDGNode1, UDGNode UDGNode2) {
            // Only connect UDGNodes if both exist in the graph
            if (UDGNodes.contains(UDGNode1) && UDGNodes.contains(UDGNode2)) {
                UDGNode1.addNeighbor(UDGNode2);   // Connect UDGNode1 to UDGNode2
                UDGNode2.addNeighbor(UDGNode1);   // Connect UDGNode2 to UDGNode1
            }
        }
    }

    public static void main(String[] args) {
        // Create graph UDGNodes
        UDGNode a = new UDGNode("A");
        UDGNode b = new UDGNode("B");
        UDGNode c = new UDGNode("C");

        // Create an undirected graph
        UndirectedGraph undirectedGraph = new UndirectedGraph();

        // Add UDGNodes to the graph
        undirectedGraph.addUDGNode(a);
        undirectedGraph.addUDGNode(b);
        undirectedGraph.addUDGNode(c);

        // Add undirected edges between UDGNodes
        undirectedGraph.addEdge(a, b);   // A -- B
        undirectedGraph.addEdge(a, c);   // A -- C
        undirectedGraph.addEdge(c, b);   // C -- B

        // Print all connections in the graph
        for (UDGNode UDGNode : undirectedGraph.UDGNodes) {
            for (UDGNode neighbor : UDGNode.neighbors) {
                System.out.println(UDGNode.value + " -- " + neighbor.value);  // Display each edge
            }
        }
    }
}