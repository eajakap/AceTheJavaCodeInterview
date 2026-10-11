package org.educative.modules.module2.maps;

import org.educative.modules.module2.maps.TaskSchedulerSolution.Task;
import org.educative.modules.module2.maps.TaskSchedulerSolution.TaskScheduler;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskSchedulerSolutionTest {
    private static final LocalDate EARLY = LocalDate.of(2023, 4, 10);
    private static final LocalDate LATE = LocalDate.of(2023, 4, 15);

    @Test
    void taskComparesByDueDate() {
        assertTrue(new Task("B", EARLY).compareTo(new Task("A", LATE)) < 0);
        assertEquals(0, new Task("A", EARLY).compareTo(new Task("B", EARLY)));
    }

    @Test
    void taskEqualsHashCodeAndToStringUseTitleAndDueDate() {
        Task task = new Task("Pay Bills", EARLY);

        assertEquals(task, new Task("Pay Bills", EARLY));
        assertEquals(task.hashCode(), new Task("Pay Bills", EARLY).hashCode());
        assertNotEquals(task, new Task("Pay Bills", LATE));
        assertNotEquals(task, new Task("Other", EARLY));
        assertEquals("Pay Bills", task.getTitle());
        assertEquals(EARLY, task.getDueDate());
        assertEquals("Task{title='Pay Bills', dueDate=2023-04-10}", task.toString());
    }

    @Test
    void getNextTaskReturnsEarliestDueTask() {
        TaskScheduler scheduler = new TaskScheduler();
        scheduler.scheduleTask(new Task("Write Report", LATE));
        scheduler.scheduleTask(new Task("Pay Bills", EARLY));

        assertEquals(new Task("Pay Bills", EARLY), scheduler.getNextTask());
    }

    @Test
    void schedulingSameDueDateReplacesExistingTask() {
        TaskScheduler scheduler = new TaskScheduler();
        scheduler.scheduleTask(new Task("First", EARLY));
        scheduler.scheduleTask(new Task("Second", EARLY));

        assertEquals("Second", scheduler.getNextTask().getTitle());
    }

    @Test
    void getNextTaskOnEmptySchedulerThrows() {
        assertThrows(NullPointerException.class, () -> new TaskScheduler().getNextTask());
    }

    @Test
    void mainPrintsEarliestTask() {
        List<String> lines = ConsoleCapture.linesPrintedBy(() -> TaskSchedulerSolution.main(new String[0]));

        assertEquals(List.of("Task{title='Pay Bills', dueDate=2023-04-10}"), lines);
    }
}
