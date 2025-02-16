package phone;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import phone.ui.MainWindow;

/**
 * A GUI for Phone using FXML.
 */
public class Main extends Application {

    private Phone phone = new Phone();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);

            // Set application icon
            stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/images/phoneBot.jpg")));

            // Set application name
            stage.setTitle("PhoneGPT");

            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setPhone(phone);  // inject the Phone instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
