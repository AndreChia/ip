import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Phone {
    public static void main(String[] args) {
        System.out.println("Hello! I'm PHONE\nWhat can I do for you?");
        Scanner sc = new Scanner(System.in);
        List<Task> activities = new ArrayList<>();
        String command;

        while (true) {
            command = sc.nextLine();
            String[] parts = command.split(" ");

            if (parts[0].equals("bye")) {
                System.out.println("Bye. I miss you Jady Myint!");
                break;
            }
            else if (parts[0].equals("list")) {
                for (int i = 0; i < activities.size(); i++) {
                    String status = activities.get(i).getStatus();
                    System.out.println(i + 1 + ".[" + status + "] " + activities.get(i).getName());
                }
            }
            else if (parts[0].equals("mark")) {
                Task t = activities.get(Integer.parseInt(parts[1])-1);
                System.out.println("Nice! I've marked this task as done: ");
                System.out.println("[X] " + t.getName());
                t.flipDone();
            }
            else if (parts[0].equals("unmark")) {
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
