package phone.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

import phone.DialogBox;
import phone.Phone;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Phone phone;

    private Image userImage;
    private Image phoneImage;

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        // Load images correctly
        userImage = new Image(this.getClass().getResourceAsStream("/images/sora1.jpg"));
        phoneImage = new Image(this.getClass().getResourceAsStream("/images/sora2.jpg"));
    }

    /**
     * Sets the Phone instance.
     *
     * @param p The chatbot instance.
     */
    public void setPhone(Phone p) {
        this.phone = p;
    }

    /**
     * Handles user input when the button is clicked or Enter is pressed.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = phone.getResponse(input); // Get chatbot's response

        // Add both user input and chatbot response to the VBox
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),   // User's message with user image
                DialogBox.getDukeDialog(response, phoneImage) // Chatbot's response with chatbot image
        );

        userInput.clear(); // Clear the input field after processing
    }


}
