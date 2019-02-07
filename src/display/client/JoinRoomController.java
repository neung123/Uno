package display.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import static display.client.StartMenuController.clientPlayer;

public class JoinRoomController {
    @FXML
    ImageView imageView;
    @FXML
    TextField textField;
    @FXML
    Button button;

    public static int roomToJoin;

    public void initialize(){
        Image background = new Image(getClass().getResourceAsStream("/createRoom.jpg"));
        imageView.setImage(background);
    }

    public void handleEnter(){
        roomToJoin = Integer.parseInt(textField.getText());
        clientPlayer.sendMessage(String.format("#joinRoom,%d,%d",roomToJoin,clientPlayer.getID()));

        getStage().close();
    }

    public Stage getStage(){
        return (Stage) textField.getScene().getWindow();
    }
}
