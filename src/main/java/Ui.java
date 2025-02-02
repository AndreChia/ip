import java.util.Scanner;

/**
 * Handles user interactions.
 */
public class Ui {
    private Scanner scanner;

    /**
     * Constructor for Ui.
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
}
