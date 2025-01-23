public class Event extends Task {
    private String startTime;
    private String endTime;
    public Event(String name, String startTime, String endTime) {
        super(name);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    protected String getStartTime() {
        return this.startTime;
    }

    protected String getEndTime() {
        return this.endTime;
    }
}
