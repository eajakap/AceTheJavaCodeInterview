package org.codesignal.debugging.problems;

import java.util.*;

public class TaskDependencyManagement {
    private Map<String, Boolean> tasks;
    private Map<String, Map<String, Object>> taskAttributes;
    private Map<String, List<String>> dependencyTasksMap;
    private Map<String, List<String>> dependentTasksMap;

    public TaskDependencyManagement() {
        tasks = new HashMap<>();
        taskAttributes = new HashMap<>();
        dependencyTasksMap = new HashMap<>();
        dependentTasksMap = new HashMap<>();

    }

    public boolean addTask(String taskId, String title, String description, String status, int priority) {
        if (tasks.containsKey(taskId)) {
            return false;
        }
        tasks.put(taskId, true);
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("title", title);
        attributes.put("description", description);
        attributes.put("status", status);
        attributes.put("priority", priority);
        taskAttributes.put(taskId, attributes);
        return true;
    }

    public boolean updateTask(String taskId, String title, String description, String status, Integer priority) {
        if (!tasks.containsKey(taskId)) {
            return false;
        }
        Map<String, Object> attributes = taskAttributes.get(taskId);
        if (title != null) {
            attributes.put("title", title);
        }
        if (description != null) {
            attributes.put("description", description);
        }
        if (status != null) {
            attributes.put("status", status);
            List<String> dependencies = dependencyTasksMap.getOrDefault(
                    taskId, Collections.emptyList());
            if ("closed".equalsIgnoreCase(status)
                    && !dependencies.isEmpty()
                    && dependencies.stream().anyMatch(dependencyTaskId -> !isTaskClosed(dependencyTaskId))) {
                attributes.put("status", "open");
            }
            if (isTaskClosed(taskId)) {
                for (String dependentTaskId : dependentTasksMap.getOrDefault(
                        taskId, Collections.emptyList())) {
                    List<String> dependentDependencies = dependencyTasksMap.getOrDefault(
                            dependentTaskId, Collections.emptyList());
                    if (dependentDependencies.stream().allMatch(this::isTaskClosed)) {
                        taskAttributes.get(dependentTaskId).put("status", "Open");
                    }
                }
            }
        }
        if (priority != null) {
            attributes.put("priority", priority);
        }
        return true;
    }

    private boolean isTaskClosed(String taskId) {
        Map<String, Object> attributes = taskAttributes.get(taskId);
        Object status = attributes == null ? null : attributes.get("status");
        return status instanceof String taskStatus && "closed".equalsIgnoreCase(taskStatus);
    }

    public boolean removeTask(String taskId) {
        if (tasks.containsKey(taskId)) {
            for (String dependencyTaskId : new ArrayList<>(
                    dependencyTasksMap.getOrDefault(taskId, Collections.emptyList()))) {
                removeDependency(taskId, dependencyTaskId);
            }
            for (String dependentTaskId : new ArrayList<>(
                    dependentTasksMap.getOrDefault(taskId, Collections.emptyList()))) {
                removeDependency(dependentTaskId, taskId);
            }

            tasks.remove(taskId);
            taskAttributes.remove(taskId);
            return true;
        }
        return false;
    }

    public List<String> getTasksByStatus(String status) {
        List<String> taskIds = new ArrayList<>();
        for (Map.Entry<String, Map<String, Object>> entry : taskAttributes.entrySet()) {
            if (status.equals(entry.getValue().get("status"))) {
                taskIds.add(entry.getKey());
            }
        }
        Collections.sort(taskIds);
        return taskIds;
    }

    public List<String> getTasksByPriority(int priority) {
        List<String> taskIds = new ArrayList<>();
        for (Map.Entry<String, Map<String, Object>> entry : taskAttributes.entrySet()) {
            if (priority == (int) entry.getValue().get("priority")) {
                taskIds.add(entry.getKey());
            }
        }
        Collections.sort(taskIds);
        return taskIds;
    }

    public boolean addDependency(String taskId, String dependencyTaskId) {
        if (!tasks.containsKey(taskId) || !tasks.containsKey(dependencyTaskId)) {
            return false;
        }
        if (taskId.equals(dependencyTaskId)
                || dependencyTasksMap.getOrDefault(taskId, Collections.emptyList()).contains(dependencyTaskId)
                || hasDependencyPath(dependencyTaskId, taskId)) {
            return false;
        }
        dependencyTasksMap.computeIfAbsent(taskId, k -> new ArrayList<>()).add(dependencyTaskId);
        dependentTasksMap.computeIfAbsent(dependencyTaskId, k -> new ArrayList<>()).add(taskId);
        return true;
    }

    private boolean hasDependencyPath(String startTaskId, String targetTaskId) {
        Set<String> visited = new HashSet<>();
        Deque<String> tasksToVisit = new ArrayDeque<>();
        tasksToVisit.push(startTaskId);

        while (!tasksToVisit.isEmpty()) {
            String currentTaskId = tasksToVisit.pop();
            if (currentTaskId.equals(targetTaskId)) {
                return true;
            }
            if (visited.add(currentTaskId)) {
                tasksToVisit.addAll(dependencyTasksMap.getOrDefault(
                        currentTaskId, Collections.emptyList()));
            }
        }
        return false;
    }

    public boolean removeDependency(String taskId, String dependencyTaskId) {
        List<String> dependencyTasks = dependencyTasksMap.get(taskId);
        if (dependencyTasks == null || !dependencyTasks.remove(dependencyTaskId)) {
            return false;
        }

        List<String> dependentTasks = dependentTasksMap.get(dependencyTaskId);
        if (dependentTasks != null) {
            dependentTasks.remove(taskId);
            if (dependentTasks.isEmpty()) {
                dependentTasksMap.remove(dependencyTaskId);
            }
        }
        if (dependencyTasks.isEmpty()) {
            dependencyTasksMap.remove(taskId);
        }
        return true;
    }

    public List<String> getDependentTasks(String taskId) {
        return new ArrayList<>(dependentTasksMap.getOrDefault(taskId, Collections.emptyList()));
    }

    public static void main(String[] args) {
        TaskDependencyManagement taskManager = new TaskDependencyManagement();
        taskManager.addTask("task1", "Title 1", "Description 1", "open", 1);
        taskManager.addTask("task2", "Title 2", "Description 2", "open", 2);
        taskManager.addTask("task3", "Title 3", "Description 3", "open", 3);
        System.out.println("tasks:" + taskManager.getTasksByStatus("open"));

        taskManager.addDependency("task2", "task1");
        taskManager.addDependency("task3", "task2");

        System.out.println("task1->" + taskManager.getDependentTasks("task1")); // Should print [task2]
        System.out.println("task2->" + taskManager.getDependentTasks("task2")); // Should print [task3]
        System.out.println("task3->" + taskManager.getDependentTasks("task3")); // Should print []

        taskManager.updateTask("task1", null, null, "closed", null);
        System.out.println("tasks:" + taskManager.getTasksByStatus("open")); // Should print [task1, task2, task3]

        // remove a dependency and check the dependent tasks again
        taskManager.removeDependency("task2", "task1");
        System.out.println("task1->" + taskManager.getDependentTasks("task1")); // Should print []
    }
}