package display.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import network.Client;

import java.io.IOException;

import static display.client.StartMenuController.exceptionAlert;

public class ClientToServController {
    @FXML
    TextField portText;
    @FXML
    TextField hostText;

    public static String host;
    public static int portNum;
    final public static int DEFAULT_PORT = 500;
    
    public void handleEnter(){
        try {
            portNum = Integer.parseInt(portText.getText());
            host = hostText.getText();
        }catch (NumberFormatException e){
            portNum = DEFAULT_PORT;
            host = "localhost";
        }

        try {
            Parent root = FXMLLoader.load(getClass().getResource("StartMenu.fxml"));
            Scene secondScene = new Scene(root);

            // New window (Stage)
            Stage stage = new Stage();

            stage.setTitle("Uno");
            stage.setScene(secondScene);
            stage.setResizable(false);
            stage.sizeToScene();

            // Specifies the modality for new window.
            stage.initModality(Modality.WINDOW_MODAL);


            stage.show();
        }catch (IOException e){
            exceptionAlert("Can not open StartMenu.fxml");
        }

        getStage().close();

    }

    public Stage getStage(){
        return (Stage) portText.getScene().getWindow();
    }
    
}
