import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;

public class Phone {
    public static void main(String[] args) {
        System.out.println("Hello! I'm PHONE\nWhat can I do for you?");
        Scanner sc = new Scanner(System.in);
        List<Task> activities = Storage.loadTasks(); // Load saved tasks from file

        while (true) {
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Bro, you typed nothing. Try a command like 'todo', 'deadline', etc.");
                continue;
            }

            String[] parts = input.split(" ", 2);
            String command = parts[0];
            String name = (parts.length > 1) ? parts[1].trim() : "";

            if (command.equals("bye")) {
                System.out.println("Bye. I miss you Jady Myint!");
                break;
            }

            else if (command.equals("list")) {
                if (activities.isEmpty()) {
                    System.out.println("Your task list is empty, bro.");
                } else {
                    for (int i = 0; i < activities.size(); i++) {
                        Task t = activities.get(i);
                        String type = t.getType();
                        String status = t.getStatus();
                        String dueDate = "";
                        String taskName = t.getName();

                        if (t instanceof Deadline) {
                            Deadline d = (Deadline) t;
                            dueDate = " (by: " + d.getDueDate() + ")";
                        } else if (t instanceof Event) {
                            Event e = (Event) t;
                            dueDate = " (from: " + e.getStartTime() + " to: " + e.getEndTime() + ")";
                        }

                        System.out.println((i + 1) + ".[" + type + "][" + status + "] " + taskName + dueDate);
                    }
                }
            }

            else if (command.equals("todo")) {
                if (name.isEmpty()) {
                    System.out.println("Bro, you have to specify what the todo is.");
                    continue;
                }
                ToDo todo = new ToDo(name);
                activities.add(todo);
                Storage.saveTasks(activities); // Save after adding
                System.out.println("Got it. I've added this task:\n    [T][ ] " + todo.getName());
            }

            else if (command.equals("deadline")) {
                if (!name.contains("/by")) {
                    System.out.println("Invalid format. Use 'deadline <desc> /by <DayOfWeek>' e.g., 'deadline return book /by Sunday'");
                    continue;
                }
                try {
                    String[] deadlineParts = name.split("/by");
                    String taskName = deadlineParts[0].trim();
                    String dayOfWeekStr = deadlineParts[1].trim();

                    DayOfWeek day = DayOfWeek.valueOf(dayOfWeekStr.toUpperCase());
                    String dueDate = LocalDate.now()
                            .with(TemporalAdjusters.previousOrSame(day))
                            .format(DateTimeFormatter.ofPattern("MMMM d'th'"));

                    Deadline deadline = new Deadline(taskName, dueDate);
                    activities.add(deadline);
                    Storage.saveTasks(activities); // Save after adding

                    System.out.println("Got it. I've added this task:\n    [D][ ] " + taskName + " (by: " + dueDate + ")");
                } catch (Exception e) {
                    System.out.println("Invalid day format. Use a valid day like 'Sunday'.");
                }
            }

            else if (command.equals("event")) {
                if (!name.contains("/from") || !name.contains("/to")) {
                    System.out.println("Invalid format. Use 'event <desc> /from <start> /to <end>'");
                    continue;
                }
                try {
                    String[] fromSplit = name.split("/from");
                    String taskName = fromSplit[0].trim();
                    String[] toSplit = fromSplit[1].split("/to");
                    String startTime = toSplit[0].trim();
                    String endTime = toSplit[1].trim();

                    Event event = new Event(taskName, startTime, endTime);
                    activities.add(event);
                    Storage.saveTasks(activities); // Save after adding

                    System.out.println("Got it. I've added this task:\n    [E][ ] " + event.getName()
                            + " (from: " + startTime + " to: " + endTime + ")");
                } catch (Exception e) {
                    System.out.println("Invalid event format. Use 'event <desc> /from <start> /to <end>'");
                }
            }

            else if (command.equals("mark")) {
                try {
                    int index = Integer.parseInt(name) - 1;
                    Task t = activities.get(index);
                    t.flipDone();
                    Storage.saveTasks(activities); // Save after marking
                    System.out.println("Nice! I've marked this task as done: [X] " + t.getName());
                } catch (Exception e) {
                    System.out.println("Invalid task number.");
                }
            }

            else if (command.equals("unmark")) {
                try {
                    int index = Integer.parseInt(name) - 1;
                    Task t = activities.get(index);
                    t.flipDone();
                    Storage.saveTasks(activities); // Save after unmarking
                    System.out.println("OK, I've marked this task as not done yet: [ ] " + t.getName());
                } catch (Exception e) {
                    System.out.println("Invalid task number.");
                }
            }

            else if (command.equals("delete")) {
                try {
                    int index = Integer.parseInt(name) - 1;
                    Task removedTask = activities.remove(index);
                    Storage.saveTasks(activities); // Save after deleting
                    System.out.println("Noted. I've removed this task:\n[" + removedTask.getType() + "][" +
                            removedTask.getStatus() + "] " + removedTask.getName());
                } catch (Exception e) {
                    System.out.println("Invalid task number.");
                }
            }

            else {
                System.out.println("Sorry bro, I don't know what '" + command + "' means.");
            }
        }

        sc.close();
    }
}
