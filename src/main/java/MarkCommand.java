/**
 * Handles marking tasks as done.
 */
public class MarkCommand extends Command {
    private String taskIndex;

    /**
     * Constructor for MarkCommand.
     *
     * @param taskIndex Index of the task to mark.
     */
    public MarkCommand(String taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            int index = Integer.parseInt(taskIndex) - 1;
            Task task = tasks.getTask(index);
            task.flipDone();
            storage.saveTasks(tasks.getTasks());
            ui.showMessage("Nice! I've marked this task as done:\n    " + task.toString());
        } catch (NumberFormatException e) {
            ui.showError("Invalid task number. Usage: mark <taskIndex> (e.g., mark 2).");
        } catch (IndexOutOfBoundsException e) {
            ui.showError("Task index out of range. You have only " + tasks.size() + " tasks.");
        }
    }
}
