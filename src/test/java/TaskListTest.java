import phone.TaskList;
import phone.task.Task;
import phone.task.ToDo;
import java.util.List;
import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


class TaskListTest {
    @Test
    void testAddTask() {
        TaskList taskList = new TaskList();
        Task task = new ToDo("Read book");

        taskList.addTask(task);
        List<Task> tasks = taskList.getTasks();

        assertEquals(1, tasks.size());
        assertEquals(task, tasks.get(0));
    }

    @Test
    void testDeleteTask() {
        TaskList taskList = new TaskList();
        Task task1 = new ToDo("Read book");
        Task task2 = new ToDo("Write notes");

        taskList.addTask(task1);
        taskList.addTask(task2);
        taskList.deleteTask(0);

        List<Task> tasks = taskList.getTasks();
        assertEquals(1, tasks.size());
        assertEquals(task2, tasks.get(0));
    }

    @Test
    void testMarkTask() {
        TaskList taskList = new TaskList();
        Task task = new ToDo("Do homework");

        taskList.addTask(task);
        taskList.markTask(0);

        assertEquals("X", taskList.getTask(0).getStatus());
    }
}
