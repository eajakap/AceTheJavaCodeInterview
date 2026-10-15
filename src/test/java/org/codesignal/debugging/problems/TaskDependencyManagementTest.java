package org.codesignal.debugging.problems;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskDependencyManagementTest {
    @Test
    void testAddTask() {
        TaskDependencyManagement solution = new TaskDependencyManagement();

        assertTrue(solution.addTask("A", "Title A", "Description A", "Open", 1));
        assertFalse(solution.addTask("A", "Duplicate", "Duplicate", "Closed", 2));
        assertEquals(List.of("A"), solution.getTasksByStatus("Open"));
        assertEquals(List.of("A"), solution.getTasksByPriority(1));
    }

    @Test
    void testRemoveTaskCleansUpDependenciesAndDependents() {
        TaskDependencyManagement solution = new TaskDependencyManagement();
        addTasks(solution, "A", "B", "C");
        assertTrue(solution.addDependency("A", "B"));
        assertTrue(solution.addDependency("B", "C"));

        assertTrue(solution.removeTask("B"));
        assertEquals(List.of(), solution.getDependentTasks("A"));
        assertEquals(List.of(), solution.getDependentTasks("C"));

        assertTrue(solution.addTask("B", "B", "", "pending", 1));
        assertTrue(solution.addDependency("A", "B"));
        assertTrue(solution.addDependency("B", "C"));
        assertEquals(List.of("A"), solution.getDependentTasks("B"));
        assertEquals(List.of("B"), solution.getDependentTasks("C"));
    }

    @Test
    void testCircularDependencyFailure() {
        TaskDependencyManagement solution = new TaskDependencyManagement();
        addTasks(solution, "task1", "task2", "task3");

        assertTrue(solution.addDependency("task1", "task2"));
        assertTrue(solution.addDependency("task2", "task3"));
        assertFalse(solution.addDependency("task3", "task1"));
    }

    @Test
    void testLongCycleFailure() {
        TaskDependencyManagement solution = new TaskDependencyManagement();
        addTasks(solution, "t1", "t2", "t3", "t4", "t5");
        assertTrue(solution.addDependency("t1", "t2"));
        assertTrue(solution.addDependency("t2", "t3"));
        assertTrue(solution.addDependency("t3", "t4"));
        assertTrue(solution.addDependency("t4", "t5"));

        // closing the whole chain, and closing it part-way along
        assertFalse(solution.addDependency("t5", "t1"));
        assertFalse(solution.addDependency("t5", "t3"));

        // a rejected add must not leave a half-written edge behind
        assertEquals(List.of(), solution.getDependentTasks("t1"));
        assertEquals(List.of("t2"), solution.getDependentTasks("t3"));

        // a shortcut across an existing path is still acyclic, so it is allowed
        assertTrue(solution.addDependency("t1", "t4"));
        assertEquals(List.of("t3", "t1"), solution.getDependentTasks("t4"));
    }

    @Test
    void testDiamondDependencyIsAllowed() {
        TaskDependencyManagement solution = new TaskDependencyManagement();
        addTasks(solution, "A", "B", "C", "D");

        assertTrue(solution.addDependency("A", "B"));
        assertTrue(solution.addDependency("A", "C"));
        assertTrue(solution.addDependency("B", "D"));
        assertTrue(solution.addDependency("C", "D"));

        assertEquals(List.of(), solution.getDependentTasks("A"));
        assertEquals(List.of("A"), solution.getDependentTasks("B"));
        assertEquals(List.of("A"), solution.getDependentTasks("C"));
        assertEquals(List.of("B", "C"), solution.getDependentTasks("D"));

        // reaching the shared tail by two paths must not mask a real cycle
        assertFalse(solution.addDependency("D", "A"));
        assertEquals(List.of(), solution.getDependentTasks("A"));
    }

    @Test
    void testRemoveWithDependency() {
        TaskDependencyManagement solution = new TaskDependencyManagement();
        addTasks(solution, "A", "B", "C");
        assertTrue(solution.addDependency("A", "B"));
        assertTrue(solution.addDependency("C", "B"));

        assertTrue(solution.removeDependency("A", "B"));
        assertEquals(List.of("C"), solution.getDependentTasks("B"));
        assertFalse(solution.removeDependency("A", "B"));
        assertTrue(solution.addDependency("A", "B"));
        assertEquals(List.of("C", "A"), solution.getDependentTasks("B"));
    }

    @Test
    void testAddDependencyAndDependentTasks() {
        TaskDependencyManagement solution = new TaskDependencyManagement();
        addTasks(solution, "A", "B");

        assertTrue(solution.addDependency("A", "B"));
        assertEquals(List.of(), solution.getDependentTasks("A"));
        assertEquals(List.of("A"), solution.getDependentTasks("B"));
    }

    @Test
    void testRemoveTaskWithDependents() {
        TaskDependencyManagement solution = new TaskDependencyManagement();
        addTasks(solution, "A", "B", "C");
        assertTrue(solution.addDependency("A", "B"));
        assertTrue(solution.addDependency("C", "B"));

        assertTrue(solution.removeTask("B"));
        assertEquals(List.of(), solution.getDependentTasks("B"));
        assertTrue(solution.addTask("B", "B", "", "pending", 1));
        assertTrue(solution.addDependency("A", "B"));
        assertEquals(List.of("A"), solution.getDependentTasks("B"));
    }

    @Test
    void testRemoveTask() {
        TaskDependencyManagement solution = new TaskDependencyManagement();
        addTasks(solution, "A", "B");

        assertTrue(solution.removeTask("A"));
        assertFalse(solution.removeTask("A"));
        assertEquals(List.of("B"), solution.getTasksByStatus("pending"));
        assertFalse(solution.addDependency("A", "B"));
    }

    @Test
    void testRemoveDependencyUpdatesDependents() {
        TaskDependencyManagement solution = new TaskDependencyManagement();
        addTasks(solution, "A", "B", "C");
        assertTrue(solution.addDependency("A", "B"));
        assertTrue(solution.addDependency("C", "B"));

        assertTrue(solution.removeDependency("A", "B"));
        assertEquals(List.of("C"), solution.getDependentTasks("B"));
        assertTrue(solution.removeDependency("C", "B"));
        assertEquals(List.of(), solution.getDependentTasks("B"));
        assertFalse(solution.removeDependency("C", "B"));
    }

    @Test
    void testDependentTaskAutoOpen() {
        TaskDependencyManagement solution = new TaskDependencyManagement();
        addTasks(solution, "A", "B", "C");
        assertTrue(solution.addDependency("B", "A"));
        assertTrue(solution.addDependency("B", "C"));
        assertTrue(solution.updateTask("B", null, null, "Blocked", null));

        assertTrue(solution.updateTask("A", null, null, "Closed", null));
        assertEquals(List.of(), solution.getTasksByStatus("Open"));

        assertTrue(solution.updateTask("C", null, null, "closed", null));
        assertEquals(List.of("B"), solution.getTasksByStatus("open"));
    }

    @Test
    void testUpdateTaskAndGetTasksByStatus() {
        TaskDependencyManagement solution = new TaskDependencyManagement();
        addTasks(solution, "A", "B", "C");

        assertTrue(solution.updateTask("B", "Updated B", "New description", "In Progress", 2));
        assertTrue(solution.updateTask("A", null, null, "In Progress", null));

        assertEquals(List.of("A", "B"), solution.getTasksByStatus("In Progress"));
        assertEquals(List.of("C"), solution.getTasksByStatus("pending"));
        assertEquals(List.of("B"), solution.getTasksByPriority(2));
    }

    @Test
    void testDependentTasksReturnsAnIndependentList() {
        TaskDependencyManagement solution = new TaskDependencyManagement();
        addTasks(solution, "A", "B");
        solution.addDependency("A", "B");

        solution.getDependentTasks("B").clear();

        assertEquals(List.of("A"), solution.getDependentTasks("B"));
    }

    private void addTasks(TaskDependencyManagement solution, String... taskIds) {
        for (String taskId : taskIds) {
            assertTrue(solution.addTask(taskId, taskId, "", "pending", 1));
        }
    }
}
