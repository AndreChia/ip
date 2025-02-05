package phone.command;

import phone.Storage;
import phone.TaskList;
import phone.Ui;

/**
 * Handles exiting the chatbot.
 */
public class ExitCommand extends Command {
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
        return null;
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
