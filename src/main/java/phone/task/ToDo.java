package phone.task;

import phone.task.Task;

/**
 * Represents a phone.task.ToDo task.
 */
public class ToDo extends Task {
    /**
     * Constructor for phone.task.ToDo.
     *
     * @param name phone.task.Task name.
     */
    public ToDo(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return "T";
    }

    @Override
    public String toFileFormat() {
        return "T | " + (getStatus().equals("X") ? "1" : "0") + " | " + getName();
    }
}
