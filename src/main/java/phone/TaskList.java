package phone;

import java.util.ArrayList;
import java.util.List;

import phone.task.Task;

/**
 * Represents a list of tasks.
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with existing tasks.
     *
     * @param tasks List of tasks to initialize.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Adds a task to the list.
     *
     * @param task Task to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the list by index.
     *
     * @param index Index of the task to delete (0-based).
     */
    public void deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
        } else {
            throw new IndexOutOfBoundsException("Task index out of range.");
        }
    }

    /**
     * Marks a task as done.
     *
     * @param index Index of the task to mark as done (0-based).
     */
    public void markTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).flipDone();
        } else {
            throw new IndexOutOfBoundsException("Task index out of range.");
        }
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return Size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Retrieves a task at a specific index.
     *
     * @param index Index of the task to retrieve (0-based).
     * @return Task at the specified index.
     */
    public Task getTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            return tasks.get(index);
        } else {
            throw new IndexOutOfBoundsException("Task index out of range.");
        }
    }

    /**
     * Returns the list of tasks.
     *
     * @return List of tasks.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Returns the formatted task list as a string for display in the GUI.
     */
    public String getFormattedTaskList() {
        if (tasks.isEmpty()) {
            return "Bro, you got no tasks yet!";
        }
        StringBuilder sb = new StringBuilder("Here's your task list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString();
    }

}
