import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Storage {
    private static final String FILE_PATH = "./data/duke.txt";

    public static List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            System.out.println("No previous tasks found. Creating new task list.");
            return tasks;
        }

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
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
                    task.flipDone(); // Mark as done
                }
                tasks.add(task);
            }
            System.out.println("Loaded " + tasks.size() + " tasks from file.");
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
        } catch (Exception e) {
            System.out.println("Error reading file. It might be corrupted.");
        }
        return tasks;
    }

    public static void saveTasks(List<Task> tasks) {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs(); // Ensure the ./data/ directory exists

        try (FileWriter writer = new FileWriter(file)) {
            for (Task task : tasks) {
                writer.write(task.toFileFormat() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }
}
