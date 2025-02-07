package phone.command;

import phone.Storage;
import phone.TaskList;
import phone.Ui;

/**
 * Handles listing all tasks.
 */
public class ListCommand extends Command {
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return tasks.getFormattedTaskList(); // Returns the formatted list instead of calling UI methods.
    }
}
