public class Task {
    private String name;
    private boolean isDone;

    public Task(String name) {
        this.name = name;
        this.isDone = false;
    }

    protected String getStatus() {
        return this.isDone ? "X" : " ";
    }

    protected String getName() {
        return this.name;
    }

    protected String getType() {
        if (this instanceof ToDo) return "T";
        if (this instanceof Deadline) return "D";
        if (this instanceof Event) return "E";
        return "unknown";
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void flipDone() {
        this.isDone = !this.isDone;
    }

    // Convert task into a format for saving in the file
    public String toFileFormat() {
        return getType() + " | " + (isDone ? "1" : "0") + " | " + name;
    }
}
