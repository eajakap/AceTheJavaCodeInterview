package org.educative.modules.module2.graphs;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CloneGraphTest {
    private static Set<CloneGraph.Node> reachableFrom(CloneGraph.Node start) {
        Set<CloneGraph.Node> visited = new HashSet<>();
        java.util.Deque<CloneGraph.Node> stack = new java.util.ArrayDeque<>();
        stack.push(start);
        while (!stack.isEmpty()) {
            CloneGraph.Node node = stack.pop();
            if (visited.add(node)) {
                node.neighbors.forEach(stack::push);
            }
        }
        return visited;
    }

    @Test
    void cloningNullReturnsNull() {
        assertNull(CloneGraph.clone(null));
    }

    @Test
    void cloneOfSingleNodeHasNoNeighbors() {
        CloneGraph.Node original = new CloneGraph.Node(1);

        CloneGraph.Node copy = CloneGraph.clone(original);

        assertNotSame(original, copy);
        assertEquals(1, copy.data);
        assertTrue(copy.neighbors.isEmpty());
    }

    @Test
    void cloneIsStructurallyEqualToOriginal() {
        int[][][] graphs = {
                {{2, 3}, {1, 3}, {1, 2}},
                {{2}, {1, 3}, {2}},
                {{2, 3, 4, 5}, {1}, {1}, {1}, {1}},
                {{2}, {1}},
                {{2, 5}, {1, 3}, {2, 4}, {3, 5}, {4, 1}}
        };

        for (int[][] adjacency : graphs) {
            CloneGraph.Node original = CloneGraph.GraphUtility.createGraph(adjacency);
            CloneGraph.Node copy = CloneGraph.clone(original);

            assertEquals(CloneGraph.GraphUtility.create2DList(original), CloneGraph.GraphUtility.create2DList(copy));
        }
    }

    @Test
    void cloneSharesNoNodesWithOriginal() {
        CloneGraph.Node original = CloneGraph.GraphUtility.createGraph(new int[][]{{2, 3}, {1, 3}, {1, 2}});

        CloneGraph.Node copy = CloneGraph.clone(original);

        Set<CloneGraph.Node> originalNodes = reachableFrom(original);
        Set<CloneGraph.Node> copiedNodes = reachableFrom(copy);
        assertEquals(3, copiedNodes.size());
        for (CloneGraph.Node node : copiedNodes) {
            assertTrue(!originalNodes.contains(node));
        }
    }

    @Test
    void cloneKeepsCyclesConsistent() {
        CloneGraph.Node original = CloneGraph.GraphUtility.createGraph(new int[][]{{2}, {1}});

        CloneGraph.Node copy = CloneGraph.clone(original);

        CloneGraph.Node copyOfTwo = copy.neighbors.get(0);
        assertEquals(2, copyOfTwo.data);
        assertSame(copy, copyOfTwo.neighbors.get(0));
    }

    @Test
    void createGraphBuildsNodesFromOneBasedAdjacencyList() {
        CloneGraph.Node one = CloneGraph.GraphUtility.createGraph(new int[][]{{2}, {1, 3}, {2}});

        assertEquals(1, one.data);
        assertEquals(List.of(List.of(2), List.of(1, 3), List.of(2)), CloneGraph.GraphUtility.create2DList(one));
    }

    @Test
    void mainReportsDeepCopyForEveryCase() {
        String output = ConsoleOutputCapture.capture(() -> CloneGraph.main(new String[0]));

        assertEquals(5, output.split("Is deep copy: true", -1).length - 1);
        assertTrue(output.contains("\tResult: [[2, 3], [1, 3], [1, 2]]"));
    }
}
