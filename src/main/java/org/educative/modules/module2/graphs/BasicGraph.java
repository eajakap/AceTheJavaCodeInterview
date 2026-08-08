package org.educative.modules.module2.graphs;

import java.util.ArrayList;
import java.util.List;

class Node {
    String value;
    List<Node> neighbors;

    public Node(String value) {
        this.value = value;
        this.neighbors = new ArrayList<>();
    }
}

public class BasicGraph {

    private static void printNeighbours(Node node) {
        for (Node neighbor : node.neighbors) {
            System.out.println("Neighbor of " + node.value + " is: " + neighbor.value);
        }

    }
    public static void main(String[] args) {
        // Create nodes
        Node a = new Node("A");
        Node b = new Node("B");
        Node c = new Node("C");
        Node d = new Node("D");

        // Create connections (edges)
        a.neighbors = new ArrayList<>(List.of(b, c));
        b.neighbors = new ArrayList<>(List.of(a, d));
        c.neighbors = new ArrayList<>(List.of(a, d));
        d.neighbors = new ArrayList<>(List.of(b, c));

        // Print neighbors of A
        printNeighbours(a);
        System.out.println("_".repeat(20));
        printNeighbours(b);
        System.out.println("_".repeat(20));
        printNeighbours(c);
        System.out.println("_".repeat(20));
        printNeighbours(d);
    }
}