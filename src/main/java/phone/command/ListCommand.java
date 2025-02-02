package phone.command;

import phone.Storage;
import phone.TaskList;
import phone.Ui;

/**
 * Handles listing all tasks.
 */
public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (tasks.size() == 0) {
            ui.showMessage("Your task list is empty.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                ui.showMessage((i + 1) + ". " + tasks.getTask(i).toString()); // No extra formatting
            }
        }
    }
}
