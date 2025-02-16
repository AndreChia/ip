package phone.command;

import phone.Storage;
import phone.TaskList;
import phone.Ui;
import phone.clientmanagement.ClientList;

/**
 * Command to list all clients.
 */
public class ListClientCommand extends Command {

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        ClientList clients = new ClientList();
        clients.setClients(storage.loadClients());  // Load clients from storage

        if (clients.getClients().isEmpty()) {
            return "No clients found.";
        }

        StringBuilder output = new StringBuilder("Here are your clients:\n");
        for (int i = 0; i < clients.getClients().size(); i++) {
            output.append((i + 1)).append(". ").append(clients.getClients().get(i)).append("\n");
        }
        return output.toString();
    }
}
