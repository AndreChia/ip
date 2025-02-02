package phone;

import phone.command.Command;

/**
 * The main class that initializes and runs the chatbot.
 */
public class Phone {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructor for phone.Phone.
     * Initializes phone.Ui, phone.Storage, and phone.TaskList.
     *
     * @param filePath Path to the storage file.
     */
    public Phone(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.loadTasks());
    }

    /**
     * Runs the chatbot until the user exits.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            String fullCommand = ui.readCommand();
            ui.showLine();

            // Pass `tasks` and `ui` to Parser for `find` command support
            Command command = Parser.parse(fullCommand, tasks, ui);

            if (command != null) {
                command.execute(tasks, ui, storage);
                isExit = command.isExit();
            }

            ui.showLine();
        }
    }

    /**
     * Main entry point.
     */
    public static void main(String[] args) {
        new Phone("data/duke.txt").run();
    }
}
