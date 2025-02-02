package phone;

import phone.command.*;

/**
 * Parses user input and returns corresponding command.
 */
public class Parser {
    /**
     * Parses user input into a phone.command.Command.
     *
     * @param userInput The user's input.
     * @return Corresponding phone.command.Command.
     */
    public static Command parse(String userInput) {
        String[] parts = userInput.split(" ", 2);
        String command = parts[0];
        String arguments = (parts.length > 1) ? parts[1] : "";

        switch (command) {
            case "bye":
                return new ExitCommand();
            case "list":
                return new ListCommand();
            case "todo":
                return new AddCommand(arguments, "todo");
            case "deadline":
                return new AddCommand(arguments, "deadline");
            case "event":
                return new AddCommand(arguments, "event");
            case "mark":
                return new MarkCommand(arguments);
            case "unmark":
                return new UnmarkCommand(arguments);
            case "delete":
                return new DeleteCommand(arguments);
            default:
                return new InvalidCommand();
        }
    }
}
