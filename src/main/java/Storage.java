import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles saving and loading of tasks from a file.
 */
public class Storage {
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
                        task = new Deadline(parts[2], parts[3]);
                        break;
                    case "E":
                        task = new Event(parts[2], parts[3], parts[4]);
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
}
