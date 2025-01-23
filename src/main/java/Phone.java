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
        List<Task> activities = new ArrayList<>();

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

                        // If it's a deadline, show the due date
                        if (type.equals("D")) {
                            Deadline d = (Deadline) t;
                            dueDate = " (by: " + d.getDueDate() + ")";
                            // If your name field contains "/by", remove it from printing
                            String[] temp = taskName.split("/by", 2);
                            taskName = temp[0].trim();
                        }
                        System.out.println((i + 1) + ".[" + type + "][" + status + "] " + taskName + dueDate);
                    }
                }
            }

            else if (command.equals("todo")) {
                if (name.isEmpty()) {
                    System.out.println("Bro, you have to specify what the todo is e.g., 'todo borrow book'");
                    continue;
                }
                ToDo todo = new ToDo(name);
                activities.add(todo);
                System.out.println("Got it. I've added this task:");
                System.out.println("    [T][ ] " + todo.getName());
                System.out.println("Now you have " + activities.size() + " tasks in the list.");
            }

            else if (command.equals("deadline")) {
                // Must contain "/by", or it’s invalid
                if (!name.contains("/by")) {
                    System.out.println("Invalid deadline format. Use 'deadline <desc> /by <DayOfWeek>' e.g., 'deadline return book /by Sunday'");
                    continue;
                }
                try {
                    // e.g.: "deadline return book /by Sunday"
                    String[] deadlineParts = name.split("/by");
                    String taskName = deadlineParts[0].trim(); // "return book"
                    String dayOfWeekStr = deadlineParts[1].trim(); // "Sunday"

                    DayOfWeek day = DayOfWeek.valueOf(dayOfWeekStr.toUpperCase());
                    String dueDate = LocalDate.now()
                            .with(TemporalAdjusters.previousOrSame(day))
                            .format(DateTimeFormatter.ofPattern("MMMM d'th'"));

                    Deadline deadline = new Deadline(name, dueDate);
                    activities.add(deadline);

                    System.out.println("Got it. I've added this task:");
                    System.out.println("    [D][ ] " + taskName + " (by: " + dueDate + ")");
                    System.out.println("Now you have " + activities.size() + " tasks in the list.");

                } catch (IllegalArgumentException e) {
                    System.out.println("Bro, '" + name + "' is not a valid day of the week. Try something like 'Sunday'.");
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Something is off with your '/by' format. Try 'deadline <desc> /by Sunday'.");
                }
            }
            else if (command.equals("event")) {
                if (!name.contains("/from") || !name.contains("/to")) {
                    System.out.println("Invalid event format. Use 'event <desc> /from <start> /to <end>'");
                    continue;
                }
                try {
                    String[] fromSplit = name.split("/from");
                    String taskName = fromSplit[0].trim(); // e.g. "project meeting"

                    String[] toSplit = fromSplit[1].split("/to");
                    String startTime = toSplit[0].trim(); // e.g. "Mon 2pm"
                    String endTime = toSplit[1].trim();   // e.g. "4pm"

                    Event event = new Event(taskName, startTime, endTime);
                    activities.add(event);

                    System.out.println("Got it. I've added this task:");
                    System.out.println("    [E][ ] " + event.getName()
                            + " (from: " + startTime + " to: " + endTime + ")");
                    System.out.println("Now you have " + activities.size() + " tasks in the list.");

                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Bro, you must provide '/from ... /to ...'. For example:\n"
                            + "event project meeting /from Mon 2pm /to 4pm");
                }
            }
            else if (command.equals("mark")) {
                try {
                    int index = Integer.parseInt(name) - 1;
                    Task t = activities.get(index);
                    t.flipDone();
                    System.out.println("Nice! I've marked this task as done: ");
                    System.out.println("[X] " + t.getName());
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
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("[ ] " + t.getName());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid task number. Usage: unmark <taskIndex> (e.g., unmark 2).");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Task index out of range. You have only " + activities.size() + " tasks.");
                }
            }
            else if (command.equals("delete")) {
                try {
                    int index = Integer.parseInt(name) - 1;
                    Task t = activities.get(index);
                    activities.remove(index);
                    System.out.println("Noted. I've removed this task:");
                    System.out.println("[" + t.getType() + "][" + t.getStatus() + "] " + t.getName());
                    System.out.println("Now you have " + activities.size() + " tasks in the list.");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid task number. Usage: delete <taskIndex> (e.g., delete 3).");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Task index out of range. You have only " + activities.size() + " tasks.");
                }
            }
            else {
                System.out.println("Sorry bro, I'm too high right now to know what '"
                        + command + "' means. Try 'todo', 'deadline', etc.");
            }
        }

        sc.close();
    }
}
