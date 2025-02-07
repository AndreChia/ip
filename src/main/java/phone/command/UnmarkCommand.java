package phone.command;

import phone.Storage;
import phone.TaskList;
import phone.Ui;
import phone.task.Task;

/**
 * Handles unmarking tasks.
 */
public class UnmarkCommand extends Command {
    private String taskIndex;

    /**
     * Constructor for phone.command.UnmarkCommand.
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
            return "OK, I've marked this task as not done yet:\n  " + task.toString();
        } catch (NumberFormatException e) {
            return "Invalid task number. Usage: unmark <taskIndex> (e.g., unmark 2).";
        } catch (IndexOutOfBoundsException e) {
            return "phone.task.Task index out of range. You have only " + tasks.size() + " tasks.";
        }
    }
}
