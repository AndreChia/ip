/**
 * Handles unmarking tasks.
 */
public class UnmarkCommand extends Command {
    private String taskIndex;

    /**
     * Constructor for UnmarkCommand.
     *
     * @param taskIndex Index of the task to unmark.
     */
    public UnmarkCommand(String taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            int index = Integer.parseInt(taskIndex) - 1;
            Task task = tasks.getTask(index);
            task.flipDone();
            storage.saveTasks(tasks.getTasks());
            ui.showMessage("OK, I've marked this task as not done yet:\n    " + task.toString());
        } catch (NumberFormatException e) {
            ui.showError("Invalid task number. Usage: unmark <taskIndex> (e.g., unmark 2).");
        } catch (IndexOutOfBoundsException e) {
            ui.showError("Task index out of range. You have only " + tasks.size() + " tasks.");
        }
    }
}
