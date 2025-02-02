package phone.command;

import phone.Storage;
import phone.TaskList;
import phone.Ui;

/**
 * Abstract class representing a user command.
 */
public abstract class Command {
    /**
     * Executes the command.
     *
     * @param tasks   The task list.
     * @param ui      The UI for user interaction.
     * @param storage The storage for saving/loading tasks.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage);

    /**
     * Determines if the command exits the program.
     *
     * @return true if exiting, false otherwise.
     */
    public boolean isExit() {
        return false;
    }
}
