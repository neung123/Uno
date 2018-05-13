package display.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import static display.client.StartMenuController.clientPlayer;


public class CreateRoomController {
    @FXML
    ImageView imageView;
    @FXML
    TextField textField;
    @FXML
    Button button;

    public static String roomName;

    public void initialize(){
        Image background = new Image(getClass().getResourceAsStream("/createRoom.jpg"));
        imageView.setImage(background);
    }

    public void handleEnter(){
        roomName = textField.getText();

        clientPlayer.isCreate(roomName);

        getStage().close();
    }

    public Stage getStage(){
        return (Stage) textField.getScene().getWindow();
    }

}
