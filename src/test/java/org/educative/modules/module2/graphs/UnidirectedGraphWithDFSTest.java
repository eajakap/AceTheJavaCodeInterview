package org.educative.modules.module2.graphs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnidirectedGraphWithDFSTest {
    @Test
    void mainPrintsRecursiveThenStackBasedDfsInSameOrder() throws Exception {
        String output = ConsoleOutputCapture.capture(() -> {
            try {
                UnidirectedGraphWithDFS.main(new String[0]);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        });

        assertEquals("A B D E C F \nDFS Using stack\nA B D E C F ", output);
    }
}
