/**
 * Handles adding tasks (ToDo, Deadline, Event).
 */
public class AddCommand extends Command {
    private String description;
    private String type;

    /**
     * Constructor for AddCommand.
     *
     * @param description Task description.
     * @param type        Task type (todo, deadline, event).
     */
    public AddCommand(String description, String type) {
        this.description = description;
        this.type = type;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task task;
        try {
            switch (type) {
                case "todo":
                    task = new ToDo(description);
                    break;
                case "deadline":
                    String[] deadlineParts = description.split("/by");
                    task = new Deadline(deadlineParts[0].trim(), deadlineParts[1].trim());
                    break;
                case "event":
                    String[] eventParts = description.split("/from|/to");
                    task = new Event(eventParts[0].trim(), eventParts[1].trim(), eventParts[2].trim());
                    break;
                default:
                    ui.showError("Invalid task type.");
                    return;
            }
            tasks.addTask(task);
            storage.saveTasks(tasks.getTasks());
            ui.showMessage("Got it. I've added this task:\n    " + task.toString());
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.showError("Invalid format. Use 'todo <desc>', 'deadline <desc> /by yyyy-MM-dd HHmm', or 'event <desc> /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm'.");
        }
    }
}
