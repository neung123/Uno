package display.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import model.Player;
import network.Client;

import java.io.IOException;


public class StartMenuController {
    @FXML
    ImageView imageViewStart;
    @FXML
    TextField inputNameField;
    @FXML
    Button enterButton;
    @FXML
    Button helpButton;

    Player player;
    public static Client clientPlayer;


    @FXML
    public void initialize() {
        Image imageBG =  new Image(getClass().getResourceAsStream("/startwin.jpg"));
        Image imageHelpBT = new Image(getClass().getResourceAsStream("/button_help.png"));
        Image imageEnterBT = new Image(getClass().getResourceAsStream("/button_enter.png"));
        imageViewStart.setImage(imageBG);
        helpButton.setGraphic(new ImageView(imageHelpBT));
        enterButton.setGraphic(new ImageView(imageEnterBT));
    }


    public void handleEnter() throws IOException{
        String name = inputNameField.getText();
        if (name.equals(""))name = "Player 1";
        player = new Player(name);

        clientPlayer = new Client("localhost",1,player.getName());


        Stage stage;
        stage = (Stage) imageViewStart.getScene().getWindow();
//        Parent root = FXMLLoader.load(getClass().getResource("InUnoGame.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("WaitingRoom.fxml"));
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }


    public void handleHelp(){
        System.out.println("help");
    }

}
