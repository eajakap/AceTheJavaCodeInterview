package org.codesignal.debugging.problems;

import java.util.*;

public class TaskManagement {
    private Map<String, Task> tasks;
    private Deque<TaskAction> history;

    public TaskManagement() {
        this.tasks = new HashMap<>();
        this.history = new ArrayDeque<>();
    }

    public boolean addTask(String taskId, String description) {
        if (tasks.containsKey(taskId)) {
            return false;
        }
        Task task = new Task(description, null, null);
        history.addLast(new TaskAction("add", taskId, task));
        tasks.put(taskId, task);
        return true;
    }

    public Task getTask(String taskId) {
        return tasks.get(taskId);
    }

    public boolean editTask(String taskId, String newDescription) {
        if (!tasks.containsKey(taskId)) {
            return false;
        }
        Task task = tasks.get(taskId);
        Task previous = new Task(task.description, task.priority, task.category);
        history.addLast(new TaskAction("edit", taskId, previous));
        task.description = newDescription;
        return true;
    }

    public boolean removeTask(String taskId) {
        if (!tasks.containsKey(taskId)) {
            return false;
        }
        Task task = tasks.get(taskId);
        Task previous = new Task(task.description, task.priority, task.category);
        history.addLast(new TaskAction("remove", taskId, previous));
        tasks.remove(taskId);
        return true;
    }

    public boolean setTaskPriority(String taskId, int priority) {
        if (!tasks.containsKey(taskId)) {
            return false;
        }
        Task task = tasks.get(taskId);
        Task previous = new Task(task.description, task.priority, task.category);
        history.addLast(new TaskAction("setPriority", taskId, previous));
        task.priority = priority;
        return true;
    }

    public boolean categorizeTask(String taskId, String category) {
        if (!tasks.containsKey(taskId)) {
            return false;
        }
        Task task = tasks.get(taskId);
        Task previous = new Task(task.description, task.priority, task.category);
        history.addLast(new TaskAction("categorize", taskId, previous));
        task.category = category;
        return true;
    }

    public List<String> getTasksByPriority(int minPriority) {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Task> entry : tasks.entrySet()) {
            Task task = entry.getValue();
            if (task.priority != null && task.priority >= minPriority) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    public List<String> getTasksByCategory(String category) {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Task> entry : tasks.entrySet()) {
            Task task = entry.getValue();
            if (category == null) {
                if (task.category == null) {
                    result.add(entry.getKey());
                }
            } else if (category.equals(task.category)) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    public boolean undoLastOperation() {
        if (history.isEmpty()) {
            return false;
        }
        TaskAction lastAction = history.removeLast();
        switch (lastAction.operation) {
            case "add":
                tasks.remove(lastAction.prevTaskId);
                break;
            case "edit":
                tasks.put(lastAction.prevTaskId, lastAction.prevTask);
                break;
            case "remove":
                tasks.put(lastAction.prevTaskId, lastAction.prevTask);
                break;
            case "setPriority":
                tasks.put(lastAction.prevTaskId, lastAction.prevTask);
                break;
            case "categorize":
                tasks.put(lastAction.prevTaskId, lastAction.prevTask);
                break;
            default:
                return false;
        }
        return true;
    }

    boolean changeTaskCategory(String taskId, String newCategory) {
        if (!tasks.containsKey(taskId)) {
            return false;
        }
        Task task = tasks.get(taskId);
        Task previous = new Task(task.description, task.priority, task.category);
        history.addLast(new TaskAction("categorize", taskId, previous));
        task.category = newCategory;
        return true;
    }

    public class Task {
        String description;
        Integer priority;
        String category;

        Task(String description, Integer priority, String category) {
            this.description = description;
            this.priority = priority;
            this.category = category;
        }
    }

    public class TaskAction {
        String operation;
        String prevTaskId;
        Task prevTask;

        public TaskAction(String operation, String prevTaskId, Task prevTask) {
            this.operation = operation;
            this.prevTask = prevTask;
            this.prevTaskId = prevTaskId;
        }
    }

    public static void main(String[] args) {
        TaskManagement taskManager = new TaskManagement();
        taskManager.addTask("task1", "Description for task 1");
        taskManager.setTaskPriority("task1", 5);
        taskManager.categorizeTask("task1", "Work");

        System.out.println("Tasks with priority >= 3: " + taskManager.getTasksByPriority(3));
        System.out.println("Tasks in category 'Work': " + taskManager.getTasksByCategory("Work"));

        taskManager.editTask("task1", "Updated description for task 1");
        System.out.println("Task1 description after edit: " + taskManager.getTask("task1").description);

        taskManager.undoLastOperation();
        System.out.println("Task1 description after undo: " + taskManager.getTask("task1").description);
    }
}