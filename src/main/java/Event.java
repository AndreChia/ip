import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    public Event(String name, String startTime, String endTime) {
        super(name);
        this.startTime = LocalDateTime.parse(startTime, INPUT_FORMAT);
        this.endTime = LocalDateTime.parse(endTime, INPUT_FORMAT);
    }

    protected String getStartTime() {
        return this.startTime.format(OUTPUT_FORMAT);
    }

    protected String getEndTime() {
        return this.endTime.format(OUTPUT_FORMAT);
    }

    @Override
    public String toFileFormat() {
        return "E | " + (getStatus().equals("X") ? "1" : "0") + " | " + getName() + " | " +
                startTime.format(INPUT_FORMAT) + " | " + endTime.format(INPUT_FORMAT);
    }
}
