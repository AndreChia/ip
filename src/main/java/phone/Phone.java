package phone;

import phone.command.Command;

/**
 * The main class that initializes and runs the chatbot logic.
 */
public class Phone {

    private static final String DEFAULT_FILE_PATH = "data/duke.txt";
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
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.tasks = new TaskList(storage.loadTasks());
    }

    /**
     * Default constructor for phone.Phone.
     * Uses the default file path.
     */
    public Phone() {
        this(DEFAULT_FILE_PATH);
    }

    /**
     * Processes user input and returns a response.
     *
     * @param input The user's input.
     * @return The task executed
     */
    public String getResponse(String input) {
        Command command = Parser.parse(input);
        return command.execute(tasks, ui, storage); // Returns response from the command
    }

    public TaskList getTaskList() {
        return tasks;
    }

    public Ui getUi() {
        return ui;
    }

    public Storage getStorage() {
        return storage;
    }

}
