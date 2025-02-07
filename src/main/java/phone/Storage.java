package phone;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import phone.task.Deadline;
import phone.task.Event;
import phone.task.Task;
import phone.task.ToDo;

/**
 * Handles saving and loading of tasks from a file.
 */
public class Storage {
    private static final DateTimeFormatter INPUT_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    private static final DateTimeFormatter FILE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    private static final DateTimeFormatter[] INPUT_FORMATS = {
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm"),
            DateTimeFormatter.ofPattern("d MMM yyyy, h:mm a")
    };

    private final String filePath;

    /**
     * Constructor for Storage.
     *
     * @param filePath Path to the storage file.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file.
     *
     * @return List of tasks.
     */
    public List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("No previous tasks found. Creating new task list.");
            return tasks;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" \\| ");
                Task task;

                switch (parts[0]) {
                    case "T":
                        task = new ToDo(parts[2]);
                        break;
                    case "D":
                        LocalDateTime deadlineDate = parseDate(parts[3]);
                        if (deadlineDate == null) {
                            System.out.println("Skipping corrupted deadline entry: " + line);
                            continue;
                        }
                        task = new Deadline(parts[2], deadlineDate.format(FILE_FORMAT));
                        break;
                    case "E":
                        LocalDateTime eventStart = parseDate(parts[3]);
                        LocalDateTime eventEnd = parseDate(parts[4]);
                        if (eventStart == null || eventEnd == null) {
                            System.out.println("Skipping corrupted event entry: " + line);
                            continue;
                        }
                        task = new Event(parts[2],
                                eventStart.format(FILE_FORMAT),
                                eventEnd.format(FILE_FORMAT));
                        break;
                    default:
                        System.out.println("Skipping corrupted entry: " + line);
                        continue;
                }

                if (parts[1].equals("1")) {
                    task.flipDone();
                }
                tasks.add(task);
            }
            System.out.println("Loaded " + tasks.size() + " tasks from file.");
        } catch (Exception e) {
            System.out.println("Error reading file. It might be corrupted.");
        }
        return tasks;
    }

    /**
     * Saves tasks to the file.
     *
     * @param tasks List of tasks to save.
     */
    public void saveTasks(List<Task> tasks) {
        File file = new File(filePath);
        file.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(file)) {
            for (Task task : tasks) {
                writer.write(task.toFileFormat() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Parses a date string into LocalDateTime using multiple possible formats,
     * including "Sunday", "Mon 2pm".
     *
     * @param inputDateTime The string to parse.
     * @return LocalDateTime if successful, otherwise null.
     */
    private LocalDateTime parseDate(String inputDateTime) {
        for (DateTimeFormatter format : INPUT_FORMATS) {
            try {
                return LocalDateTime.parse(inputDateTime, format);
            } catch (DateTimeParseException ignored) {
            }
        }

        // Try parsing a day name like "Sunday" and set to the next occurrence at 6 PM
        try {
            DayOfWeek day = DayOfWeek.valueOf(inputDateTime.toUpperCase(Locale.ENGLISH));
            return LocalDate.now().with(TemporalAdjusters.next(day)).atTime(18, 0);
        } catch (Exception ignored) {
        }

        // Try parsing "Mon 2pm"
        String[] parts = inputDateTime.split(" ");
        if (parts.length == 2) {
            try {
                DayOfWeek day = DayOfWeek.valueOf(parts[0].substring(0, 1).toUpperCase() +
                        parts[0].substring(1).toLowerCase(Locale.ENGLISH));
                LocalTime time = LocalTime.parse(parts[1].replace("am", " AM").replace("pm", " PM"),
                        DateTimeFormatter.ofPattern("h a", Locale.ENGLISH));
                return LocalDate.now().with(TemporalAdjusters.next(day)).atTime(time);
            } catch (Exception ignored) {
            }
        }

        return null; // Parsing failed
    }
}
