package display;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import player.Player;

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
        System.out.println("enter");
        String name = inputNameField.getText();
        if (name.equals(""))name = "Player 1";
        player = new Player(name);


        Stage stage;
        stage = (Stage) imageViewStart.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("InGame.fxml"));
        stage.setScene(new Scene(root));
        stage.show();


    }


    public void handleHelp(){
        System.out.println("help");
    }

}
