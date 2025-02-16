package phone.clientmanagement;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages a list of clients.
 */
public class ClientList {
    private List<Client> clients = new ArrayList<>();

    /**
     * Adds a client to the list.
     *
     * @param client The client to be added.
     */
    public void addClient(Client client) {
        clients.add(client);
    }

    /**
     * Retrieves the list of clients.
     *
     * @return List of clients.
     */
    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
}

