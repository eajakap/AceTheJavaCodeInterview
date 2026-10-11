package org.educative.modules.module2.graphs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UnidirectedGraphWithBFSTest {
    private static UnidirectedGraphWithBFS.Graph sampleGraph() {
        UnidirectedGraphWithBFS.Graph g = new UnidirectedGraphWithBFS.Graph();
        g.addEdge("A", "B");
        g.addEdge("A", "C");
        g.addEdge("B", "D");
        g.addEdge("B", "E");
        g.addEdge("C", "F");
        return g;
    }

    @Test
    void bfsVisitsLevelByLevel() {
        String output = ConsoleOutputCapture.capture(() -> sampleGraph().bfs("A"));

        assertEquals("A B C D E F ", output);
    }

    @Test
    void bfsFromLeafTraversesWholeConnectedGraph() {
        String output = ConsoleOutputCapture.capture(() -> sampleGraph().bfs("F"));

        assertEquals("F C A B D E ", output);
    }

    @Test
    void bfsVisitsEachNodeOnceInCycle() {
        UnidirectedGraphWithBFS.Graph g = new UnidirectedGraphWithBFS.Graph();
        g.addEdge("X", "Y");
        g.addEdge("Y", "Z");
        g.addEdge("Z", "X");

        assertEquals("X Y Z ", ConsoleOutputCapture.capture(() -> g.bfs("X")));
    }

    @Test
    void bfsFromUnknownNodeFails() {
        assertThrows(NullPointerException.class, () -> sampleGraph().bfs("Q"));
    }

    @Test
    void mainRunsBfsFromA() {
        assertEquals("A B C D E F ",
                ConsoleOutputCapture.capture(() -> UnidirectedGraphWithBFS.Main.main(new String[0])));
    }
}
