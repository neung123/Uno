package display.client;

import card.Card;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Player;
import model.Room;
import network.CardListener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import static network.Client.player;

public class InUnoController implements CardListener{
    private ObjectInputStream inputStream = null;
    @FXML
    ImageView imageViewBG;
    @FXML
    Button placeButton;
    @FXML
    Button drawButton;
    @FXML
    Button unoButton;
    @FXML
    AnchorPane pane;


    @FXML
    public void initialize() {

//        getObjectFromClient();

        Image imageBG =  new Image(getClass().getResourceAsStream("/bg.jpg"));
        Image imagePlaceBT = new Image(getClass().getResourceAsStream("/button_place.png"));
        Image imageDrawBT = new Image(getClass().getResourceAsStream("/button_draw.png"));
        Image imageUnoBT = new Image(getClass().getResourceAsStream("/button_uno.png"));
        imageViewBG.setImage(imageBG);
        placeButton.setGraphic(new ImageView(imagePlaceBT));
        drawButton.setGraphic(new ImageView(imageDrawBT));
        unoButton.setGraphic(new ImageView(imageUnoBT));

        setUpCard();
    }


    public void setUpCard(){
        ImageView cardOne = new ImageView(new Image(getClass().getResourceAsStream("/card_all/r_0.jpg" )));

        cardOne.relocate(250, Main.HEIGHT - Card.getHeight());

        pane.getChildren().add(cardOne);
    }



//    public void getObjectFromClient() {
//        Room room = null;
//        try {
//            ServerSocket serverSocket = new ServerSocket(4445);
//            Socket socket = serverSocket.accept();
//
//
//            inputStream = new ObjectInputStream(socket.getInputStream());
//
//            room = (Room) inputStream.readObject();
//
//            socket.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        room.setListener(this);
//    }


    @Override
    public void middleCard(Card card) {
        Platform.runLater(
                () -> {
                    ImageView cardOne = new ImageView(card.getCardImage());

                    cardOne.relocate(Main.WIDTH/2, Main.HEIGHT/2);
                }
        );
    }

    @Override
    public void showMine() {

    }

    @Override
    public void showOther() {

    }
}
