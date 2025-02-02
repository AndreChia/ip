import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private LocalDateTime dueDate;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    public Deadline(String name, String dueDate) {
        super(name);
        this.dueDate = LocalDateTime.parse(dueDate, INPUT_FORMAT);
    }

    protected String getDueDate() {
        return this.dueDate.format(OUTPUT_FORMAT);
    }

    @Override
    public String toFileFormat() {
        return "D | " + (getStatus().equals("X") ? "1" : "0") + " | " + getName() + " | " + dueDate.format(INPUT_FORMAT);
    }
}
