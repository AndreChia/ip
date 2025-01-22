import java.util.Scanner;

public class Phone {
    public static void main(String[] args) {
        System.out.println("Hello! I'm PHONE");
        System.out.println("What can I do for you?");
        Scanner sc = new Scanner(System.in);
        String command;

        while (true) {
            command = sc.nextLine();
            System.out.println(command);
            if (command.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }
        }
    }
}
