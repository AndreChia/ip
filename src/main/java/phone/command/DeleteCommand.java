package phone.command;

import phone.Storage;
import phone.TaskList;
import phone.Ui;
import phone.task.Task;

/**
 * Handles deleting tasks.
 */
public class DeleteCommand extends Command {
    private String taskIndex;

    /**
     * Constructor for phone.command.DeleteCommand.
     *
     * @param taskIndex Index of the task to delete.
     */
    public DeleteCommand(String taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            int index = Integer.parseInt(taskIndex) - 1;
            Task removedTask = tasks.getTask(index);
            tasks.deleteTask(index);
            storage.saveTasks(tasks.getTasks());
            return "Noted. I've removed this task:\n    " + removedTask.toString();
        } catch (NumberFormatException e) {
            return "Invalid task number. Usage: delete <taskIndex> (e.g., delete 3).";
        } catch (IndexOutOfBoundsException e) {
            return "phone.task.Task index out of range. You have only " + tasks.size() + " tasks.";
        }
    }
}
