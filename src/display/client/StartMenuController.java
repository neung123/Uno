package display.client;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import model.Player;
import network.Client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static display.client.ClientToServController.host;
import static display.client.ClientToServController.portNum;


public class StartMenuController {
    @FXML
    ImageView imageViewStart;
    @FXML
    TextField inputNameField;
    @FXML
    Button enterButton;


    public static Client clientPlayer;


    @FXML
    public void initialize() {
        Image imageBG =  new Image(getClass().getResourceAsStream("/startwin.jpg"));
        Image imageEnterBT = new Image(getClass().getResourceAsStream("/button_enter.png"));
        imageViewStart.setImage(imageBG);

        enterButton.setGraphic(new ImageView(imageEnterBT));
    }


    public void handleEnter(){
        String name = inputNameField.getText();
        if (name.equals(""))name = "Player 1";



        try {
            clientPlayer = new Client(host, portNum, name);

            Stage stage = getStage();
            Parent root = FXMLLoader.load(getClass().getResource("WaitingRoom.fxml"));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.sizeToScene();
            stage.show();

        }catch (IOException e) {
            exceptionAlert("Can not connect to Server");
            getStage().close();
        }

    }

    public static void exceptionAlert(String statement){
        JFrame frame = new JFrame("Exception!");
        JLabel label = new JLabel(String.format(statement), SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.PLAIN, 20));
        frame.setSize(400, 150);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(label);
        frame.setVisible(true);
    }

    public Stage getStage(){
        return (Stage) imageViewStart.getScene().getWindow();
    }

}

