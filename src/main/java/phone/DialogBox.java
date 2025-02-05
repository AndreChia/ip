package phone;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(phone.ui.MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box so that Phone's messages appear on the left.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT); // Align Phone's messages to the left
    }

    /**
     * Creates a user dialog box aligned to the right.
     *
     * @param text The user input.
     * @param img The user's image.
     * @return A styled dialog box with right alignment.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        DialogBox db = new DialogBox("You: " + text, img);
        db.setAlignment(Pos.TOP_RIGHT); // Align user messages to the right
        return db;
    }

    /**
     * Creates a chatbot dialog box aligned to the left.
     *
     * @param text The chatbot's response.
     * @param img The chatbot's image.
     * @return A styled dialog box with left alignment.
     */
    public static DialogBox getDukeDialog(String text, Image img) {
        DialogBox db = new DialogBox("Phone: " + text, img);
        db.flip(); // Ensure chatbot messages appear on the left
        return db;
    }
}
