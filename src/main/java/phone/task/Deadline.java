package phone.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a phone.task.Deadline task with a due date.
 */
public class Deadline extends Task {

    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
    private LocalDateTime dueDate;

    /**
     * Constructor for phone.task.Deadline.
     *
     * @param name    phone.task.Task name.
     * @param dueDate Due date in "yyyy-MM-dd HHmm" format.
     */
    public Deadline(String name, String dueDate) {
        super(name);
        this.dueDate = LocalDateTime.parse(dueDate, INPUT_FORMAT);
    }

    /**
     * Returns the formatted due date.
     *
     * @return Formatted due date string.
     */
    public String getDueDate() {
        return this.dueDate.format(OUTPUT_FORMAT);
    }

    @Override
    public String getType() {
        return "D";
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + getDueDate() + ")";
    }

    @Override
    public String toFileFormat() {
        return "D | " + (getStatus().equals("X") ? "1" : "0") + " | "
                + getName() + " | " + dueDate.format(INPUT_FORMAT);
    }
}
