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

    protected void flipDone() {
        this.isDone = !this.isDone;
    }
}
