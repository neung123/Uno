package display.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import network.Client;

import java.io.IOException;

import static display.client.InUnoController.cards;
import static display.client.StartMenuController.clientPlayer;

public class PlaceCardController {
    @FXML
    ImageView imageView;
    @FXML
    TextField textField;
    @FXML
    Button button;

    public static int select;

    public void initialize(){
        Image background = new Image(getClass().getResourceAsStream("/createRoom.jpg"));
        imageView.setImage(background);
    }

    public void handleEnter(){
        select = Integer.parseInt(textField.getText());
        String getCard;
        try {
            getCard = cards.get(select - 1);
        }catch (ArrayIndexOutOfBoundsException e){
            return;
        }

        Platform.runLater(
                () -> {
                    clientPlayer.sendMessage(String.format("#play,%d,%s", clientPlayer.getID(),getCard));
                }
        );

        getStage().close();
    }

    public Stage getStage(){
        return (Stage) textField.getScene().getWindow();
    }
}
