package phone;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import phone.command.AddCommand;
import phone.command.DeleteCommand;
import phone.command.ExitCommand;
import phone.command.InvalidCommand;
import phone.command.ListCommand;
import phone.command.MarkCommand;
import phone.command.UnmarkCommand;

/**
 * Tests the {@link Parser} class.
 */
class ParserTest {
    @Test
    void testParseValidCommand() {
        assertTrue(Parser.parse("list") instanceof ListCommand);
        assertTrue(Parser.parse("bye") instanceof ExitCommand);
        assertTrue(Parser.parse("todo read book") instanceof AddCommand);
        assertTrue(Parser.parse("mark 1") instanceof MarkCommand);
        assertTrue(Parser.parse("unmark 1") instanceof UnmarkCommand);
        assertTrue(Parser.parse("delete 2") instanceof DeleteCommand);
    }

    @Test
    void testParseInvalidCommand() {
        assertTrue(Parser.parse("invalid command") instanceof InvalidCommand);
    }
}
