public class Deadline extends Task {
    private String dueDate;

    public Deadline(String name, String dueDate) {
        super(name);
        this.dueDate = dueDate;
    }

    protected String getDueDate() {
        return this.dueDate;
    }

    @Override
    public String toFileFormat() {
        return "D | " + (getStatus().equals("X") ? "1" : "0") + " | " + getName() + " | " + dueDate;
    }
}
