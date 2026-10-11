package org.educative.modules.module2.graphs;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UnidirectedGraphMainTest {
    @Test
    void addUdgNodeIgnoresDuplicates() {
        UnidirectedGraphMain.UndirectedGraph graph = new UnidirectedGraphMain.UndirectedGraph();
        UnidirectedGraphMain.UDGNode a = new UnidirectedGraphMain.UDGNode("A");

        graph.addUDGNode(a);
        graph.addUDGNode(a);

        assertEquals(List.of(a), graph.UDGNodes);
    }

    @Test
    void addEdgeConnectsBothDirections() {
        UnidirectedGraphMain.UndirectedGraph graph = new UnidirectedGraphMain.UndirectedGraph();
        UnidirectedGraphMain.UDGNode a = new UnidirectedGraphMain.UDGNode("A");
        UnidirectedGraphMain.UDGNode b = new UnidirectedGraphMain.UDGNode("B");
        graph.addUDGNode(a);
        graph.addUDGNode(b);

        graph.addEdge(a, b);

        assertEquals(List.of(b), a.neighbors);
        assertEquals(List.of(a), b.neighbors);
    }

    @Test
    void addEdgeIgnoresNodesNotInGraph() {
        UnidirectedGraphMain.UndirectedGraph graph = new UnidirectedGraphMain.UndirectedGraph();
        UnidirectedGraphMain.UDGNode a = new UnidirectedGraphMain.UDGNode("A");
        UnidirectedGraphMain.UDGNode outsider = new UnidirectedGraphMain.UDGNode("X");
        graph.addUDGNode(a);

        graph.addEdge(a, outsider);

        assertTrue(a.neighbors.isEmpty());
        assertTrue(outsider.neighbors.isEmpty());
    }

    @Test
    void mainPrintsEveryEdgeFromBothEnds() {
        List<String> lines = ConsoleOutputCapture.lines(() -> UnidirectedGraphMain.main(new String[0]));

        assertEquals(List.of("A -- B", "A -- C", "B -- A", "B -- C", "C -- A", "C -- B"), lines);
    }
}
