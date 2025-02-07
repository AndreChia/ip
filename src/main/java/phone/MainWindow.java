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
import phone.Parser;
import phone.Phone;
import phone.command.Command;
import phone.command.AddCommand;
import phone.command.DeleteCommand;
import phone.command.MarkCommand;
import phone.command.UnmarkCommand;
import phone.task.Deadline;
import phone.task.Event;
import phone.task.ToDo;

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
        String input = userInput.getText().trim();
        String response = phone.getResponse(input); // Directly get response from Phone

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, phoneImage));

        userInput.clear();
    }

    @FXML
    private void displayTaskList() {
        // Only refresh task list if it was modified
        String taskListString = phone.getTaskList().getFormattedTaskList();
        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog(taskListString, phoneImage));
    }

}
