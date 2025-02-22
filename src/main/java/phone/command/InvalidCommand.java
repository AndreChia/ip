package phone.command;

import phone.Storage;
import phone.TaskList;
import phone.Ui;

/**
 * Handles invalid or unrecognized user commands.
 */
public class InvalidCommand extends Command {
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return "Sorry bro, I don't know what that means. Try 'todo', 'deadline', 'event', etc.";
    }
}
