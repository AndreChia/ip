/**
 * Represents a ToDo task.
 */
public class ToDo extends Task {
    /**
     * Constructor for ToDo.
     *
     * @param name Task name.
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
