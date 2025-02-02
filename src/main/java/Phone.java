import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Phone {
    public static void main(String[] args) {
        System.out.println("Hello! I'm PHONE\nWhat can I do for you?");
        Scanner sc = new Scanner(System.in);
        List<Task> activities = Storage.loadTasks(); // Load saved tasks

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
                        String dateInfo = "";
                        String taskName = t.getName();

                        if (t instanceof Deadline) {
                            dateInfo = " (by: " + ((Deadline) t).getDueDate() + ")";
                        } else if (t instanceof Event) {
                            Event e = (Event) t;
                            dateInfo = " (from: " + e.getStartTime() + " to: " + e.getEndTime() + ")";
                        }

                        System.out.println((i + 1) + ".[" + type + "][" + status + "] " + taskName + dateInfo);
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
                Storage.saveTasks(activities);
                System.out.println("Got it. I've added this task:\n    [T][ ] " + todo.getName());
            }

            else if (command.equals("deadline")) {
                if (!name.contains("/by")) {
                    System.out.println("Invalid format. Use 'deadline <desc> /by yyyy-MM-dd HHmm'.");
                    continue;
                }
                try {
                    String[] deadlineParts = name.split("/by");
                    String taskName = deadlineParts[0].trim();
                    String dueDateStr = deadlineParts[1].trim();

                    Deadline deadline = new Deadline(taskName, dueDateStr);
                    activities.add(deadline);
                    Storage.saveTasks(activities);

                    System.out.println("Got it. I've added this task:\n    [D][ ] " + taskName + " (by: " + deadline.getDueDate() + ")");
                } catch (Exception e) {
                    System.out.println("Invalid date format. Use 'yyyy-MM-dd HHmm' (e.g., 2023-05-01 1800).");
                }
            }

            else if (command.equals("event")) {
                if (!name.contains("/from") || !name.contains("/to")) {
                    System.out.println("Invalid format. Use 'event <desc> /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm'.");
                    continue;
                }
                try {
                    String[] fromSplit = name.split("/from");
                    String taskName = fromSplit[0].trim();
                    String[] toSplit = fromSplit[1].split("/to");
                    String startTimeStr = toSplit[0].trim();
                    String endTimeStr = toSplit[1].trim();

                    Event event = new Event(taskName, startTimeStr, endTimeStr);
                    activities.add(event);
                    Storage.saveTasks(activities);

                    System.out.println("Got it. I've added this task:\n    [E][ ] " + event.getName()
                            + " (from: " + event.getStartTime() + " to: " + event.getEndTime() + ")");
                } catch (Exception e) {
                    System.out.println("Invalid date format. Use 'yyyy-MM-dd HHmm' (e.g., 2023-05-01 1800).");
                }
            }

            else if (command.equals("mark")) {
                try {
                    int index = Integer.parseInt(name) - 1;
                    Task t = activities.get(index);
                    t.flipDone();
                    Storage.saveTasks(activities);
                    System.out.println("Nice! I've marked this task as done: [X] " + t.getName());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid task number. Usage: mark <taskIndex> (e.g., mark 2).");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Task index out of range. You have only " + activities.size() + " tasks.");
                }
            }

            else if (command.equals("unmark")) {
                try {
                    int index = Integer.parseInt(name) - 1;
                    Task t = activities.get(index);
                    t.flipDone();
                    Storage.saveTasks(activities);
                    System.out.println("OK, I've marked this task as not done yet: [ ] " + t.getName());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid task number. Usage: unmark <taskIndex> (e.g., unmark 2).");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Task index out of range. You have only " + activities.size() + " tasks.");
                }
            }

            else if (command.equals("delete")) {
                try {
                    int index = Integer.parseInt(name) - 1;
                    Task removedTask = activities.remove(index);
                    Storage.saveTasks(activities);
                    System.out.println("Noted. I've removed this task:\n[" + removedTask.getType() + "][" +
                            removedTask.getStatus() + "] " + removedTask.getName());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid task number. Usage: delete <taskIndex> (e.g., delete 3).");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Task index out of range. You have only " + activities.size() + " tasks.");
                }
            }

            else {
                System.out.println("Sorry bro, I don't know what '" + command + "' means.");
            }
        }

        sc.close();
    }
}
