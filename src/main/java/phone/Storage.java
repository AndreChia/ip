package phone;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import phone.clientmanagement.Client;
import phone.clientmanagement.ClientList;
import phone.task.Deadline;
import phone.task.Event;
import phone.task.Task;
import phone.task.ToDo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

/**
 * Handles saving and loading of tasks and clients from files.
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
    private final String clientFilePath = "data/clients.txt"; // Separate file for clients

    /**
     * Constructs a Storage object with the given file path.
     *
     * @param filePath Path to the storage file for tasks.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the storage file.
     *
     * @return List of tasks loaded from the file.
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
                Task task = parseTaskLine(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
            System.out.println("Loaded " + tasks.size() + " tasks from file.");
        } catch (Exception e) {
            System.out.println("Error reading file. It might be corrupted.");
        }
        return tasks;
    }

    /**
     * Parses a line from the tasks file and creates a corresponding Task object.
     *
     * @param line The line from the tasks file.
     * @return A Task object or null if parsing fails.
     */
    private Task parseTaskLine(String line) {
        String[] parts = line.split(" \\| ");
        Task task = null;

        switch (parts[0]) {
            case "T":
                task = new ToDo(parts[2]);
                break;
            case "D":
                LocalDateTime deadlineDate = parseDate(parts[3]);
                if (deadlineDate != null) {
                    task = new Deadline(parts[2], deadlineDate.format(FILE_FORMAT));
                } else {
                    System.out.println("Skipping corrupted deadline entry: " + line);
                }
                break;
            case "E":
                LocalDateTime eventStart = parseDate(parts[3]);
                LocalDateTime eventEnd = parseDate(parts[4]);
                if (eventStart != null && eventEnd != null) {
                    task = new Event(parts[2], eventStart.format(FILE_FORMAT), eventEnd.format(FILE_FORMAT));
                } else {
                    System.out.println("Skipping corrupted event entry: " + line);
                }
                break;
            default:
                System.out.println("Skipping corrupted entry: " + line);
                break;
        }

        if (task != null && parts[1].equals("1")) {
            task.flipDone();
        }
        return task;
    }

    /**
     * Saves tasks to the storage file.
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
     * Parses a date string into a LocalDateTime using multiple formats.
     *
     * @param inputDateTime The string to parse.
     * @return LocalDateTime object if successful, otherwise null.
     */
    private LocalDateTime parseDate(String inputDateTime) {
        for (DateTimeFormatter format : INPUT_FORMATS) {
            try {
                return LocalDateTime.parse(inputDateTime, format);
            } catch (DateTimeParseException ignored) {
            }
        }

        try {
            DayOfWeek day = DayOfWeek.valueOf(inputDateTime.toUpperCase(Locale.ENGLISH));
            return LocalDate.now().with(TemporalAdjusters.next(day)).atTime(18, 0);
        } catch (Exception ignored) {
        }

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
        return null;
    }

    /**
     * Loads clients from the client file.
     *
     * @return A list of clients.
     */
    public List<Client> loadClients() {
        List<Client> clients = new ArrayList<>();
        File file = new File(clientFilePath);

        if (!file.exists()) {
            System.out.println("No previous clients found. Creating new client list.");
            return clients;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" \\| ");
                if (parts.length == 3) {
                    clients.add(new Client(parts[0], parts[1], parts[2]));
                } else {
                    System.out.println("Skipping corrupted client entry: " + line);
                }
            }
            System.out.println("Loaded " + clients.size() + " clients from file.");
        } catch (Exception e) {
            System.out.println("Error reading client file. It might be corrupted.");
        }
        return clients;
    }

    /**
     * Saves clients to the client file.
     *
     * @param clients List of clients to save.
     */
    public void saveClients(List<Client> clients) {
        File file = new File(clientFilePath);
        file.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(file)) {
            for (Client client : clients) {
                writer.write(client.toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving clients: " + e.getMessage());
        }
    }
}
