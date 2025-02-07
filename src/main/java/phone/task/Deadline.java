package phone.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Deadline task with a due date.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("d MMM yyyy, h:mm a");

    private static final DateTimeFormatter FILE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    private String dueDate;

    /**
     * Constructor for Deadline.
     *
     * @param name    Task name.
     * @param dueDate Due date as a String.
     */
    public Deadline(String name, String dueDate) {
        super(name);
        this.dueDate = dueDate;
    }

    /**
     * Retrieves the due date formatted for display.
     *
     * @return Formatted due date string.
     */
    public String getFormattedDueDate() {
        return dueDate;
    }

    @Override
    public String getType() {
        return "D";
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + getFormattedDueDate() + ")";
    }

    @Override
    public String toFileFormat() {
        return "D | " + (getStatus().equals("X") ? "1" : "0") + " | " + getName() + " | " + dueDate;
    }
}
