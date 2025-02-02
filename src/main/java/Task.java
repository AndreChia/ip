/**
 * Represents a general task.
 */
public abstract class Task {
    private String name;
    private boolean isDone;

    /**
     * Constructor for Task.
     *
     * @param name Task name.
     */
    public Task(String name) {
        this.name = name;
        this.isDone = false;
    }

    /**
     * Returns the status icon (X for done, space for not done).
     *
     * @return Status icon as a string.
     */
    public String getStatus() {
        return isDone ? "X" : " ";
    }

    /**
     * Returns the task name.
     *
     * @return Task name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the task type (T, D, E).
     *
     * @return Task type.
     */
    public abstract String getType();

    /**
     * Toggles the completion status of the task.
     */
    public void flipDone() {
        this.isDone = !this.isDone;
    }

    /**
     * Converts the task to a human-readable string.
     *
     * @return Formatted string representation.
     */
    @Override
    public String toString() {
        return "[" + getType() + "][" + getStatus() + "] " + name;
    }

    /**
     * Converts the task to a format suitable for saving in a file.
     *
     * @return Formatted string for storage.
     */
    public abstract String toFileFormat();
}
