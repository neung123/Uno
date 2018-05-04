package display.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import network.MessageListener;

public class WaitingRoomController implements MessageListener{
    @FXML
    ImageView background;
    @FXML
    TextArea chatLog;
    @FXML
    TextField field;
    @FXML
    Button sendButton;
    @FXML
    TextArea roomLog;
    @FXML
    Button joinButton;
    @FXML
    Button createButton;



    public void initialize(){
        StartMenuController.clientPlayer.setListener(this);

        Image imageBG =  new Image(getClass().getResourceAsStream("/waitingroom.jpg"));
        Image imageSendBT = new Image(getClass().getResourceAsStream("/button_send.png"));
        Image imageJoinBT = new Image(getClass().getResourceAsStream("/button_join.png"));
        Image imageCreateBT = new Image(getClass().getResourceAsStream("/button_create.png"));
        background.setImage(imageBG);
        sendButton.setGraphic(new javafx.scene.image.ImageView(imageSendBT));
        joinButton.setGraphic(new javafx.scene.image.ImageView(imageJoinBT));
        createButton.setGraphic(new javafx.scene.image.ImageView(imageCreateBT));
        chatLog.setEditable(false);
        roomLog.setEditable(false);

    }

    public void handleSend(){
        if(field.getText().trim().equals("")) return;

        String msg = field.getText();

        StartMenuController.clientPlayer.sendMessage(msg);

        field.setText("");
    }

    @Override
    public void onMessage(String message) {
        Platform.runLater(
                () -> {
                    chatLog.appendText(message);
                }
        );
    }

    @Override
    public void onLog(String message) {

    }
}
