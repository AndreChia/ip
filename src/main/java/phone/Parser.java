package phone;

import phone.command.*;

/**
 * Parses user input and returns corresponding command.
 */
public class Parser {
    /**
     * Parses user input into a {@link Command}.
     *
     * @param userInput The user's input.
     * @return Corresponding {@link Command}.
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
            case "client":
                String[] clientArgs = arguments.split(" ", 2);
                if (clientArgs[0].equals("add")) {
                    String[] details = clientArgs[1].split(" ", 3);
                    if (details.length < 3) {
                        return new InvalidCommand(); // Handle invalid client add command
                    }
                    return new AddClientCommand(details[0], details[1], details[2]);
                } else if (clientArgs[0].equals("list")) {
                    return new ListClientCommand();
                } else {
                    return new InvalidCommand();
                }

            default:
                return new InvalidCommand(); // Ensures unknown commands don't return null
        }
    }
}
