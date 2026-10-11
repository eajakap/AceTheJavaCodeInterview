package org.educative.modules.module2.graphs;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasicGraphTest {
    @Test
    void nodeStartsWithNoNeighbors() {
        BasicGraph.Node node = new BasicGraph.Node("A");

        assertEquals("A", node.value);
        assertEquals(List.of(), node.neighbors);
    }

    @Test
    void mainPrintsNeighborsOfEveryNode() {
        List<String> lines = ConsoleOutputCapture.lines(() -> BasicGraph.main(new String[0]));

        assertEquals(List.of(
                "Neighbor of A is: B",
                "Neighbor of A is: C",
                "____________________",
                "Neighbor of B is: A",
                "Neighbor of B is: D",
                "____________________",
                "Neighbor of C is: A",
                "Neighbor of C is: D",
                "____________________",
                "Neighbor of D is: B",
                "Neighbor of D is: C"
        ), lines);
    }
}
