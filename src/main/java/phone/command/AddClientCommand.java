package phone.command;

import phone.Storage;
import phone.TaskList;
import phone.Ui;
import phone.clientmanagement.Client;
import phone.clientmanagement.ClientList;

/**
 * Command to add a new client to the client list.
 */
public class AddClientCommand extends Command {
    private final String name;
    private final String phone;
    private final String email;

    /**
     * Constructs an AddClientCommand with client details.
     *
     * @param name  Client's name.
     * @param phone Client's phone number.
     * @param email Client's email address.
     */
    public AddClientCommand(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    /**
     * Executes the command to add a client.
     *
     * @param tasks   The task list (not used for client operations).
     * @param ui      The UI for interaction.
     * @param storage The storage handler.
     * @return Response message after adding the client.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        ClientList clientList = new ClientList();
        clientList.setClients(storage.loadClients()); // Load existing clients
        Client client = new Client(name, phone, email);
        clientList.addClient(client);
        storage.saveClients(clientList.getClients()); // Save after adding
        return "New client added: " + client.toString();
    }
}
