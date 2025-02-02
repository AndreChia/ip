package phone.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an phone.task.Event task with a start and end time.
 */
public class Event extends Task {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    /**
     * Constructor for phone.task.Event.
     *
     * @param name      phone.task.Task name.
     * @param startTime Start time in "yyyy-MM-dd HHmm" format.
     * @param endTime   End time in "yyyy-MM-dd HHmm" format.
     */
    public Event(String name, String startTime, String endTime) {
        super(name);
        this.startTime = LocalDateTime.parse(startTime, INPUT_FORMAT);
        this.endTime = LocalDateTime.parse(endTime, INPUT_FORMAT);
    }

    /**
     * Returns the formatted start time.
     *
     * @return Formatted start time string.
     */
    public String getStartTime() {
        return this.startTime.format(OUTPUT_FORMAT);
    }

    /**
     * Returns the formatted end time.
     *
     * @return Formatted end time string.
     */
    public String getEndTime() {
        return this.endTime.format(OUTPUT_FORMAT);
    }

    @Override
    public String getType() {
        return "E";
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + getStartTime() + " to: " + getEndTime() + ")";
    }

    @Override
    public String toFileFormat() {
        return "E | " + (getStatus().equals("X") ? "1" : "0") + " | " + getName() + " | " +
                startTime.format(INPUT_FORMAT) + " | " + endTime.format(INPUT_FORMAT);
    }
}
