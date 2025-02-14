package phone.command;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

import phone.Storage;
import phone.TaskList;
import phone.Ui;
import phone.task.Deadline;
import phone.task.Event;
import phone.task.Task;
import phone.task.ToDo;

/**
 * Handles adding tasks (ToDo, Deadline, Event).
 */
public class AddCommand extends Command {

    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("d MMM yyyy, h:mm a");

    private static final DateTimeFormatter[] DATE_FORMATS = {
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm"),
            DateTimeFormatter.ofPattern("d MMM yyyy, h:mm a")
    };

    private static final DateTimeFormatter[] TIME_FORMATS = {
            DateTimeFormatter.ofPattern("HHmm"),
            DateTimeFormatter.ofPattern("H:mm"),
            DateTimeFormatter.ofPattern("h a", Locale.ENGLISH)
    };

    private final String description;
    private final String type;

    /**
     * Constructor for AddCommand.
     *
     * @param description Task description (includes date/time parts).
     * @param type        Task type (todo, deadline, event).
     */
    public AddCommand(String description, String type) {
        this.description = description.trim();
        this.type = type.toLowerCase();
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            switch (type) {
                case "todo":
                    return handleTodo(tasks, storage);

                case "deadline":
                    return handleDeadline(tasks, storage);

                case "event":
                    return handleEvent(tasks, storage);

                default:
                    return "Invalid task type. Use: todo, deadline, or event.";
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return "Invalid format! Please follow the correct format for " + type + ".";
        }
    }

    /**
     * Handles adding a ToDo task.
     *
     * @param tasks   TaskList to which the new Task is added.
     * @param storage Storage used to persist tasks.
     * @return Result message to display to the user.
     */
    private String handleTodo(TaskList tasks, Storage storage) {
        // Create a ToDo with the entire 'description' as the content
        Task task = new ToDo(description);
        tasks.addTask(task);
        storage.saveTasks(tasks.getTasks());

        return buildAddMessage(task, tasks.size());
    }

    /**
     * Handles adding a Deadline task.
     *
     * @param tasks   TaskList to which the new Task is added.
     * @param storage Storage used to persist tasks.
     * @return Result message to display to the user.
     */
    private String handleDeadline(TaskList tasks, Storage storage) {
        String[] deadlineParts = description.split("/by", 2);
        if (deadlineParts.length < 2) {
            return "Invalid deadline format! "
                    + "Use: 'deadline <desc> /by yyyy-MM-dd HHmm' or similar.";
        }

        LocalDateTime deadlineDateTime = parseDateTime(deadlineParts[1].trim(), null);
        if (deadlineDateTime == null) {
            return "Invalid deadline date format! "
                    + "Try 'yyyy-MM-dd HHmm', 'Sunday', or 'Mon 2pm'.";
        }

        String deadlineFormatted = deadlineDateTime.format(OUTPUT_FORMAT);
        Task task = new Deadline(deadlineParts[0].trim(), deadlineFormatted);

        tasks.addTask(task);
        storage.saveTasks(tasks.getTasks());
        return buildAddMessage(task, tasks.size());
    }

    /**
     * Handles adding an Event task.
     *
     * @param tasks   TaskList to which the new Task is added.
     * @param storage Storage used to persist tasks.
     * @return Result message to display to the user.
     */
    private String handleEvent(TaskList tasks, Storage storage) {
        String[] eventParts = description.split("/from|/to", 3);
        if (eventParts.length < 3) {
            return "Invalid event format! "
                    + "Use: 'event <desc> /from <date/time> /to <date/time>'.";
        }

        LocalDateTime startDateTime = parseDateTime(eventParts[1].trim(), null);
        if (startDateTime == null) {
            return "Could not parse the event start time! "
                    + "Use valid formats (e.g. 'Mon 2pm').";
        }

        // Parse end, referencing the start for possible time-only inputs
        LocalDateTime endDateTime = parseDateTime(eventParts[2].trim(), startDateTime);
        if (endDateTime == null) {
            return "Could not parse the event end time! "
                    + "Use valid formats or time-only (e.g. '4pm').";
        }

        String startFormatted = startDateTime.format(OUTPUT_FORMAT);
        String endFormatted = endDateTime.format(OUTPUT_FORMAT);
        Task task = new Event(eventParts[0].trim(), startFormatted, endFormatted);

        tasks.addTask(task);
        storage.saveTasks(tasks.getTasks());
        return buildAddMessage(task, tasks.size());
    }

    /**
     * Builds the success message after adding a task.
     *
     * @param task Newly added task.
     * @param totalTasks Current total number of tasks in the list.
     * @return The formatted success message.
     */
    private String buildAddMessage(Task task, int totalTasks) {
        return "Got it. I've added this task:\n    "
                + task.toString()
                + "\nNow you have " + totalTasks + " tasks in the list.";
    }

    // -------------------------------------------------------
    // Existing parseDateTime(...) and parseDayOfWeek(...) below
    // -------------------------------------------------------

    private LocalDateTime parseDateTime(String inputDateTime, LocalDateTime referenceDateTime) {
        // (unchanged from your code)
        for (DateTimeFormatter format : DATE_FORMATS) {
            try {
                return LocalDateTime.parse(inputDateTime, format);
            } catch (DateTimeParseException ignored) {
                // Try next format
            }
        }

        DayOfWeek possibleDay = parseDayOfWeek(inputDateTime);
        if (possibleDay != null) {
            return LocalDate.now()
                    .with(TemporalAdjusters.next(possibleDay))
                    .atTime(18, 0);
        }

        String[] parts = inputDateTime.split(" ");
        if (parts.length == 2) {
            DayOfWeek day = parseDayOfWeek(parts[0]);
            if (day != null) {
                String cleanedTime = parts[1].replace("am", " AM").replace("pm", " PM");
                try {
                    LocalTime time = LocalTime.parse(cleanedTime,
                            DateTimeFormatter.ofPattern("h a", Locale.ENGLISH));
                    return LocalDate.now()
                            .with(TemporalAdjusters.next(day))
                            .atTime(time);
                } catch (DateTimeParseException ignored) {
                    // Not a valid time
                }
            }
        }

        if (referenceDateTime != null) {
            for (DateTimeFormatter timeFormat : TIME_FORMATS) {
                try {
                    String cleaned = inputDateTime.replace("am", " AM").replace("pm", " PM");
                    LocalTime time = LocalTime.parse(cleaned, timeFormat);
                    return referenceDateTime.toLocalDate().atTime(time);
                } catch (DateTimeParseException ignored) {
                    // Try next time format
                }
            }
        }

        return null;
    }

    private DayOfWeek parseDayOfWeek(String input) {
        // (unchanged from your code)
        String upper = input.toUpperCase(Locale.ENGLISH);
        switch (upper) {
            case "SUN":
            case "SUNDAY":
                return DayOfWeek.SUNDAY;
            case "MON":
            case "MONDAY":
                return DayOfWeek.MONDAY;
            case "TUE":
            case "TUES":
            case "TUESDAY":
                return DayOfWeek.TUESDAY;
            case "WED":
            case "WEDS":
            case "WEDNESDAY":
                return DayOfWeek.WEDNESDAY;
            case "THU":
            case "THUR":
            case "THURS":
            case "THURSDAY":
                return DayOfWeek.THURSDAY;
            case "FRI":
            case "FRIDAY":
                return DayOfWeek.FRIDAY;
            case "SAT":
            case "SATURDAY":
                return DayOfWeek.SATURDAY;
            default:
                return null;
        }
    }
}
