package org.codesignal.debugging.problems;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskManagementTest {
    @Test
    void testAddAndRemoveTask() {
        TaskManagement manager = new TaskManagement();

        assertTrue(manager.addTask("task1", "Description 1"));
        assertFalse(manager.addTask("task1", "Duplicate"));
        assertEquals("Description 1", manager.getTask("task1").description);

        assertTrue(manager.removeTask("task1"));
        assertNull(manager.getTask("task1"));
        assertFalse(manager.removeTask("task1"));
    }

    @Test
    void testEditTaskAndUndo() {
        TaskManagement manager = new TaskManagement();
        manager.addTask("task1", "Original");

        assertTrue(manager.editTask("task1", "Updated"));
        assertEquals("Updated", manager.getTask("task1").description);
        assertFalse(manager.editTask("missing", "Nope"));

        assertTrue(manager.undoLastOperation());
        assertEquals("Original", manager.getTask("task1").description);
    }

    @Test
    void testUndoRemoveTaskPreservesDetails() {
        TaskManagement manager = new TaskManagement();
        manager.addTask("task1", "Description");
        manager.setTaskPriority("task1", 4);
        manager.categorizeTask("task1", "Work");
        manager.removeTask("task1");

        assertTrue(manager.undoLastOperation());

        TaskManagement.Task restored = manager.getTask("task1");
        assertEquals("Description", restored.description);
        assertEquals(4, restored.priority);
        assertEquals("Work", restored.category);
    }

    @Test
    void testUndoRemoveTask() {
        TaskManagement manager = new TaskManagement();
        manager.addTask("task1", "Description");
        manager.removeTask("task1");
        assertNull(manager.getTask("task1"));

        assertTrue(manager.undoLastOperation());
        assertEquals("Description", manager.getTask("task1").description);
    }

    @Test
    void testUndoEditTask() {
        TaskManagement manager = new TaskManagement();
        manager.addTask("task1", "First");
        manager.editTask("task1", "Second");
        manager.editTask("task1", "Third");

        assertTrue(manager.undoLastOperation());
        assertEquals("Second", manager.getTask("task1").description);
        assertTrue(manager.undoLastOperation());
        assertEquals("First", manager.getTask("task1").description);
    }

    @Test
    void testUndoAddTask() {
        TaskManagement manager = new TaskManagement();
        manager.addTask("task1", "Description");

        assertTrue(manager.undoLastOperation());
        assertNull(manager.getTask("task1"));
        assertTrue(manager.addTask("task1", "Added again"));
    }

    @Test
    void testAddCategorizeAndChangeCategory() {
        TaskManagement manager = new TaskManagement();
        manager.addTask("task1", "Description");

        assertTrue(manager.categorizeTask("task1", "Work"));
        assertEquals("Work", manager.getTask("task1").category);
        assertEquals(List.of("task1"), manager.getTasksByCategory("Work"));

        assertTrue(manager.changeTaskCategory("task1", "Home"));
        assertEquals("Home", manager.getTask("task1").category);
        assertEquals(List.of(), manager.getTasksByCategory("Work"));
        assertEquals(List.of("task1"), manager.getTasksByCategory("Home"));

        assertFalse(manager.categorizeTask("missing", "Work"));
        assertFalse(manager.changeTaskCategory("missing", "Work"));
    }

    @Test
    void testAddSetPriorityAndFetchByPriority() {
        TaskManagement manager = new TaskManagement();
        manager.addTask("task1", "One");
        manager.addTask("task2", "Two");
        manager.addTask("task3", "Three");
        manager.setTaskPriority("task1", 5);
        manager.setTaskPriority("task2", 2);

        assertEquals(List.of("task1"), sorted(manager.getTasksByPriority(3)));
        assertEquals(List.of("task1", "task2"), sorted(manager.getTasksByPriority(2)));
        assertFalse(manager.setTaskPriority("missing", 1));
    }

    @Test
    void testUndoSetTaskPriority() {
        TaskManagement manager = new TaskManagement();
        manager.addTask("task1", "Description");
        manager.setTaskPriority("task1", 3);
        manager.setTaskPriority("task1", 9);

        assertTrue(manager.undoLastOperation());
        assertEquals(3, manager.getTask("task1").priority);
        assertTrue(manager.undoLastOperation());
        assertNull(manager.getTask("task1").priority);
    }

    @Test
    void testUndoCategorizeTask() {
        TaskManagement manager = new TaskManagement();
        manager.addTask("task1", "Description");
        manager.categorizeTask("task1", "Work");

        assertTrue(manager.undoLastOperation());
        assertNull(manager.getTask("task1").category);
        assertEquals(List.of("task1"), manager.getTasksByCategory(null));
    }

    @Test
    void testFetchTasksByCategory() {
        TaskManagement manager = new TaskManagement();
        manager.addTask("task1", "One");
        manager.addTask("task2", "Two");
        manager.addTask("task3", "Three");
        manager.categorizeTask("task1", "Work");
        manager.categorizeTask("task2", "Work");

        assertEquals(List.of("task1", "task2"), sorted(manager.getTasksByCategory("Work")));
        assertEquals(List.of("task3"), manager.getTasksByCategory(null));
        assertEquals(List.of(), manager.getTasksByCategory("Missing"));
    }

    @Test
    void testUndoLastOperation() {
        TaskManagement manager = new TaskManagement();
        assertFalse(manager.undoLastOperation());

        manager.addTask("task1", "Description");
        assertTrue(manager.undoLastOperation());
        assertFalse(manager.undoLastOperation());
    }

    private List<String> sorted(List<String> values) {
        List<String> copy = new ArrayList<>(values);
        Collections.sort(copy);
        return copy;
    }
}
