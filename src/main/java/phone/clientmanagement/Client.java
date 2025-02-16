package phone.clientmanagement;

/**
 * Represents a client with a name, phone number, and email.
 */
public class Client {
    private final String name;
    private final String phone;
    private final String email;

    /**
     * Creates a new client.
     *
     * @param name  Client's name.
     * @param phone Client's phone number.
     * @param email Client's email address.
     */
    public Client(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public String toString() {
        return name + " | " + phone + " | " + email;
    }

    public String getName() {
        return this.name;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getEmail() {
        return this.email;
    }
}
