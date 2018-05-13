package display.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Room;
import network.MessageListener;

import java.io.IOException;

import static display.client.StartMenuController.clientPlayer;
import static display.client.StartMenuController.exceptionAlert;

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
        clientPlayer.setListener(this);

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

        clientPlayer.sendMessage(msg);

        field.setText("");
    }

    public void setPage(String fxml,String name)throws IOException{
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Scene secondScene = new Scene(root);

            // New window (Stage)
            Stage stage = new Stage();

            stage.setTitle(name);
            stage.setScene(secondScene);
            stage.setResizable(false);
            stage.sizeToScene();

            // Specifies the modality for new window.
            stage.initModality(Modality.WINDOW_MODAL);


            stage.show();
        }catch (IOException e){
            exceptionAlert("Can not open CreateRoom.fxml");
        }
    }

    public void handleCreate() throws IOException {
        setPage("CreateRoom.fxml","Create Room");
    }

    public void handleJoin() throws IOException {
        setPage("JoinRoom.fxml","Join Room");
    }

    public Stage getStage(){
        return (Stage) background.getScene().getWindow();
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
        Platform.runLater(
                () -> {
                    roomLog.appendText(message);
                }
        );
    }

    @Override
    public void changeTo(String fxml) {

        Platform.runLater(
                () -> {
                    try {
                        Stage stage = getStage();
                        Parent root = FXMLLoader.load(getClass().getResource(fxml));
                        stage.setScene(new Scene(root));
                        stage.setResizable(false);
                        stage.sizeToScene();
                        stage.show();

                    } catch (IOException e) {
                        exceptionAlert("Can not connect to Server");
                        getStage().close();
                    }

                }
        );
    }

}
