import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Deadline task with a due date.
 */
public class Deadline extends Task {
    private LocalDateTime dueDate;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    /**
     * Constructor for Deadline.
     *
     * @param name    Task name.
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
        return "D | " + (getStatus().equals("X") ? "1" : "0") + " | " + getName() + " | " + dueDate.format(INPUT_FORMAT);
    }
}
