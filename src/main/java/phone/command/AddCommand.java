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

    /**
     * A list of common datetime formats for parsing full date+time.
     */
    private static final DateTimeFormatter[] DATE_FORMATS = {
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm"),
            DateTimeFormatter.ofPattern("d MMM yyyy, h:mm a")
    };

    /**
     * A list of time-only formats to be used if no full date parse is successful
     * but we do have a known reference date.
     *
     * Example: "1400", "4:00 PM", "4pm", etc.
     */
    private static final DateTimeFormatter[] TIME_FORMATS = {
            // 24-hour with no separator, e.g. "1400"
            DateTimeFormatter.ofPattern("HHmm"),
            // 24-hour with separator, e.g. "14:00"
            DateTimeFormatter.ofPattern("H:mm"),
            // 12-hour, e.g. "4 PM"
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
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task task;
        try {
            switch (type) {
                case "todo":
                    task = new ToDo(description);
                    break;

                case "deadline":
                    // Expected format: deadline <desc> /by <date/time string>
                    String[] deadlineParts = description.split("/by", 2);
                    if (deadlineParts.length < 2) {
                        return "Invalid deadline format! Use: 'deadline <desc> /by yyyy-MM-dd HHmm' or similar.";
                    }

                    LocalDateTime deadlineDateTime = parseDateTime(deadlineParts[1].trim(), null);
                    if (deadlineDateTime == null) {
                        return "Invalid deadline date format! "
                                + "Try something like 'yyyy-MM-dd HHmm' or 'Sunday' or 'Mon 2pm'.";
                    }

                    // Format for display/storage
                    String deadlineFormatted = deadlineDateTime.format(OUTPUT_FORMAT);
                    task = new Deadline(deadlineParts[0].trim(), deadlineFormatted);
                    break;

                case "event":
                    // Expected format: event <desc> /from <date/time> /to <date/time>
                    String[] eventParts = description.split("/from|/to", 3);
                    if (eventParts.length < 3) {
                        return "Invalid event format! Use: 'event <desc> /from <date/time> /to <date/time>'.";
                    }

                    // Parse start
                    LocalDateTime startDateTime = parseDateTime(eventParts[1].trim(), null);
                    if (startDateTime == null) {
                        return "Could not parse the event start time! Use valid formats (e.g. 'Mon 2pm').";
                    }

                    // Parse end, giving 'startDateTime' as a reference for time-only inputs
                    LocalDateTime endDateTime = parseDateTime(eventParts[2].trim(), startDateTime);
                    if (endDateTime == null) {
                        return "Could not parse the event end time! Use valid formats or time-only (e.g. '4pm').";
                    }

                    // Format for display/storage
                    String startFormatted = startDateTime.format(OUTPUT_FORMAT);
                    String endFormatted = endDateTime.format(OUTPUT_FORMAT);
                    task = new Event(eventParts[0].trim(), startFormatted, endFormatted);
                    break;

                default:
                    return "Invalid task type. Use: todo, deadline, or event.";
            }

            tasks.addTask(task);
            storage.saveTasks(tasks.getTasks());

            return "Got it. I've added this task:\n    "
                    + task.toString()
                    + "\nNow you have " + tasks.size() + " tasks in the list.";

        } catch (ArrayIndexOutOfBoundsException e) {
            return "Invalid format! Please follow the correct format for " + type + ".";
        }
    }

    /**
     * Parses a date/time string into a LocalDateTime using multiple strategies:
     * <ul>
     * <li>Common date+time patterns (e.g. "yyyy-MM-dd HHmm").</li>
     * <li>Full day-of-week only (e.g. "Sunday"), defaulting time to 6 PM.</li>
     * <li>Short or long day-of-week + time (e.g. "Mon 2pm", "Monday 2pm").</li>
     * <li>If {@code referenceDateTime} is not null, time-only patterns ("4pm")
     * applied to that date.</li>
     * </ul>
     */
    private LocalDateTime parseDateTime(String inputDateTime, LocalDateTime referenceDateTime) {
        // 1) Try the known full date+time formats
        for (DateTimeFormatter format : DATE_FORMATS) {
            try {
                return LocalDateTime.parse(inputDateTime, format);
            } catch (DateTimeParseException ignored) {
                // Try next format
            }
        }

        // 2) Check if this is a day-of-week only. (e.g. "Sunday" or "Mon")
        // We'll try to parse even short forms ("Mon") or full ("Monday").
        DayOfWeek possibleDay = parseDayOfWeek(inputDateTime);
        if (possibleDay != null) {
            // If we recognized "Mon" or "Monday", then default to next <that day> at 6 PM
            return LocalDate.now()
                    .with(TemporalAdjusters.next(possibleDay))
                    .atTime(18, 0);
        }

        // 3) Check day-of-week + time (e.g. "Mon 2pm")
        String[] parts = inputDateTime.split(" ");
        if (parts.length == 2) {
            // Try parseDayOfWeek on the first part
            DayOfWeek day = parseDayOfWeek(parts[0]);
            if (day != null) {
                // Attempt to parse time from the second part
                // e.g. "2pm" -> "2 PM"
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

        // 4) If we have a reference date/time, try time-only parsing
        if (referenceDateTime != null) {
            for (DateTimeFormatter timeFormat : TIME_FORMATS) {
                try {
                    String cleaned = inputDateTime.replace("am", " AM").replace("pm", " PM");
                    LocalTime time = LocalTime.parse(cleaned, timeFormat);
                    // Combine with reference date's date part
                    return referenceDateTime.toLocalDate().atTime(time);
                } catch (DateTimeParseException ignored) {
                    // Try next time format
                }
            }
        }

        // If all else fails, return null
        return null;
    }

    /**
     * Tries to parse both short and full forms of day-of-week.
     * e.g. "Mon" -> MONDAY, "Tues" -> TUESDAY, "Sunday" -> SUNDAY, etc.
     *
     * @param input The day-of-week string from user input.
     * @return A DayOfWeek if recognized, or null if not recognized.
     */
    private DayOfWeek parseDayOfWeek(String input) {
        // Normalize to uppercase
        String upper = input.toUpperCase(Locale.ENGLISH);

        // Check if the user typed "MON", "Monday", etc.
        // The built-in DayOfWeek.valueOf expects EXACT name: "MONDAY", "TUESDAY", ...
        // So let's do a quick map of possible short forms to the full name.
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
