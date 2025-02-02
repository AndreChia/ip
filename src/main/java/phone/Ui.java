package phone;

import phone.task.Task;

import java.util.List;
import java.util.Scanner;

/**
 * Handles user interactions.
 */
public class Ui {
    private Scanner scanner;

    /**
     * Constructor for phone.Ui.
     * Initializes the Scanner.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays welcome message.
     */
    public void showWelcome() {
        System.out.println("Hello! I'm PHONE\nWhat can I do for you?");
    }

    /**
     * Reads user input.
     *
     * @return User command.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Displays a separator line.
     */
    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays a goodbye message.
     */
    public void showGoodbye() {
        System.out.println("Bye. I miss you Jady Myint!");
    }

    /**
     * Displays an error message.
     *
     * @param message Error message.
     */
    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    /**
     * Displays a given message.
     *
     * @param message Message to display.
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays matching tasks based on search results.
     *
     * @param tasks The list of tasks that match the search query.
     */
    public void showMatchingTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No matching tasks found.");
        } else {
            System.out.println("Here are the matching tasks:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

}
