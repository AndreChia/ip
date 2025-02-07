package phone.task;

/**
 * Represents an Event task with a start and end time.
 */
public class Event extends Task {
    private String startTime;
    private String endTime;

    /**
     * Constructor for Event.
     *
     * @param name      Task name.
     * @param startTime Start time as a String.
     * @param endTime   End time as a String.
     */
    public Event(String name, String startTime, String endTime) {
        super(name);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Retrieves the formatted start time.
     *
     * @return Formatted start time string.
     */
    public String getFormattedStartTime() {
        return startTime;
    }

    /**
     * Retrieves the formatted end time.
     *
     * @return Formatted end time string.
     */
    public String getFormattedEndTime() {
        return endTime;
    }

    @Override
    public String getType() {
        return "E";
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + getFormattedStartTime() + " to: " + getFormattedEndTime() + ")";
    }

    @Override
    public String toFileFormat() {
        return "E | " + (getStatus().equals("X") ? "1" : "0") + " | " + getName() + " | " + startTime + " | " + endTime;
    }
}
