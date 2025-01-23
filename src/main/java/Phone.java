import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Phone {
    public static void main(String[] args) {
        System.out.println("Hello! I'm PHONE\nWhat can I do for you?");
        Scanner sc = new Scanner(System.in);
        List<Task> activities = new ArrayList<>();
        String input, command;

        while (true) {
            input = sc.nextLine();
            String[] parts = input.split(" ", 2);
            command = parts[0];
            String name = parts.length > 1 ? parts[1] : "";

            if (command.equals("bye")) {
                System.out.println("Bye. I miss you Jady Myint!");
                break;
            }
            else if (command.equals("list")) {
                for (int i = 0; i < activities.size(); i++) {
                    Task t = activities.get(i);
                    String type = t.getType();
                    String status = t.getStatus();
                    System.out.println(i + 1 + ".[" + type + "] [" + status + "] " + activities.get(i).getName());
                }
            }
            else if (command.equals("todo")) {
                ToDo todo = new ToDo(name);
                activities.add(todo);
                System.out.println("Got it. I've added this task:");
                System.out.println("[T][ ] " + todo.getName());
                System.out.println("Now you have " + activities.size() + " tasks in the list.");
            }
            else if (command.equals("mark")) {
                Task t = activities.get(Integer.parseInt(parts[1])-1);
                System.out.println("Nice! I've marked this task as done: ");
                System.out.println("[X] " + t.getName());
                t.flipDone();
            }
            else if (command.equals("unmark")) {
                Task t = activities.get(Integer.parseInt(parts[1])-1);
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("[ ] " + t.getName());
                t.flipDone();
            }
            else {
                System.out.println("added: " + command);
                activities.add(new Task(command));
            }
        }
        sc.close();
    }
}
