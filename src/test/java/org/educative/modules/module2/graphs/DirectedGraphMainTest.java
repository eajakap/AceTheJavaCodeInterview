package org.educative.modules.module2.graphs;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DirectedGraphMainTest {
    @Test
    void addNodeIgnoresDuplicates() {
        DirectedGraphMain.DirectedGraph graph = new DirectedGraphMain.DirectedGraph();
        DirectedGraphMain.Node a = new DirectedGraphMain.Node("A");

        graph.addNode(a);
        graph.addNode(a);

        assertEquals(List.of(a), graph.Nodes);
    }

    @Test
    void addEdgeIsOneWay() {
        DirectedGraphMain.DirectedGraph graph = new DirectedGraphMain.DirectedGraph();
        DirectedGraphMain.Node a = new DirectedGraphMain.Node("A");
        DirectedGraphMain.Node b = new DirectedGraphMain.Node("B");
        graph.addNode(a);
        graph.addNode(b);

        graph.addEdge(a, b);

        assertEquals(List.of(b), a.neighbors);
        assertTrue(b.neighbors.isEmpty());
    }

    @Test
    void addEdgeIgnoresNodesNotInGraph() {
        DirectedGraphMain.DirectedGraph graph = new DirectedGraphMain.DirectedGraph();
        DirectedGraphMain.Node a = new DirectedGraphMain.Node("A");
        DirectedGraphMain.Node outsider = new DirectedGraphMain.Node("X");
        graph.addNode(a);

        graph.addEdge(a, outsider);
        graph.addEdge(outsider, a);

        assertTrue(a.neighbors.isEmpty());
        assertTrue(outsider.neighbors.isEmpty());
    }

    @Test
    void mainPrintsDirectedEdges() {
        List<String> lines = ConsoleOutputCapture.lines(() -> DirectedGraphMain.main(new String[0]));

        assertEquals(List.of("A -> B", "A -> C", "C -> B"), lines);
    }
}
