package org.educative.modules.module2.maps;

import java.time.LocalDate;
import java.util.Objects;
import java.util.TreeMap;

// TODO: Define the Task class with a constructor to initialize its title and dueDate
// Make sure to implement the compareTo method to compare tasks based on dueDate
// Also, implement equals, hashCode, and toString methods
class Task implements Comparable<Task>{
    private String title;
    private LocalDate dueDate;

    public Task(String title, LocalDate dueDate) {
        this.title = title;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return title.equals(task.title) && dueDate.equals(task.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, dueDate);
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", dueDate=" + dueDate +
                '}';
    }

    @Override
    public int compareTo(Task o) {
        return this.dueDate.compareTo(o.dueDate);
    }
}
// TODO: Define the TaskScheduler class
// It should initialize a TreeMap to hold tasks
// Include methods to schedule a task and get the next task based on due dates

class TaskScheduler {
    private TreeMap<LocalDate, Task> tasks;

    public TaskScheduler() {
        tasks = new TreeMap<>();
    }

    public void scheduleTask(Task task) {
        tasks.put(task.getDueDate(), task);
    }

    public Task getNextTask() {
        return tasks.firstEntry().getValue();
    }
}

public class TaskSchedulerSolution {
    public static void main(String[] args) {
        TaskScheduler scheduler = new TaskScheduler();

        scheduler.scheduleTask(new Task("Write Report", LocalDate.of(2023, 4, 15)));
        scheduler.scheduleTask(new Task("Pay Bills", LocalDate.of(2023, 4, 10)));

        System.out.println(scheduler.getNextTask());  // Output should be Task(title='Pay Bills', dueDate=2023-04-10)
    }

}
