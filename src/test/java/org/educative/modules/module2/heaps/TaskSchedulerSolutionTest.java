package org.educative.modules.module2.heaps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskSchedulerSolutionTest {
    @Test
    void computesLeastIntervalsForTypicalInputs() {
        TaskSchedulerSolution solver = new TaskSchedulerSolution();

        assertEquals(8, solver.leastInterval(new char[]{'A', 'A', 'A', 'B', 'B', 'B'}, 2));
        assertEquals(8, solver.leastInterval(new char[]{'A', 'A', 'A', 'B', 'B', 'B', 'C', 'C'}, 2));
    }

    @Test
    void returnsTaskCountWhenCooldownIsZero() {
        TaskSchedulerSolution solver = new TaskSchedulerSolution();

        assertEquals(4, solver.leastInterval(new char[]{'A', 'A', 'B', 'C'}, 0));
    }

    @Test
    void handlesEmptyAndSingleTaskInputs() {
        TaskSchedulerSolution solver = new TaskSchedulerSolution();

        assertEquals(0, solver.leastInterval(new char[0], 3));
        assertEquals(1, solver.leastInterval(new char[]{'Z'}, 10));
    }

    @Test
    void handlesSingleRepeatedTaskWithLargeCooldown() {
        TaskSchedulerSolution solver = new TaskSchedulerSolution();

        assertEquals(13, solver.leastInterval(new char[]{'X', 'X', 'X'}, 5));
    }
}
