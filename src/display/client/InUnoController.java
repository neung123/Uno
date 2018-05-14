package display.client;

import card.Card;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Player;
import model.Room;
import network.CardListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import static display.client.StartMenuController.clientPlayer;
import static display.client.StartMenuController.exceptionAlert;
import static network.Client.getPlayer;
import static network.Client.inRoom;
import static network.Client.player;

public class InUnoController implements CardListener{
    @FXML
    ImageView imageViewBG;
    @FXML
    Button drawButton;
    @FXML
    Button unoButton;
    @FXML
    Button place;
    @FXML
    AnchorPane pane;

    public static ArrayList<ImageView> imageViews = new ArrayList<>();
    public static ArrayList<String> cards = new ArrayList<>();


    @FXML
    public void initialize() {
        clientPlayer.setCardListener(this);
        clientPlayer.sendMessage("#setUpRoom," + inRoom);

        Image imageBG =  new Image(getClass().getResourceAsStream("/bg.jpg"));
        Image imageDrawBT = new Image(getClass().getResourceAsStream("/button_draw.png"));
        Image imageUnoBT = new Image(getClass().getResourceAsStream("/button_uno.png"));
        Image imagePlaceBT = new Image(getClass().getResourceAsStream("/button_place.png"));
        imageViewBG.setImage(imageBG);
        drawButton.setGraphic(new ImageView(imageDrawBT));
        unoButton.setGraphic(new ImageView(imageUnoBT));
        place.setGraphic(new ImageView(imagePlaceBT));
    }

    public void handlePlace() {

        if (getPlayer().isTurn()) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("PlaceCard.fxml"));
                Scene secondScene = new Scene(root);

                // New window (Stage)
                Stage stage = new Stage();

                stage.setTitle("Place Card");
                stage.setScene(secondScene);
                stage.setResizable(false);
                stage.sizeToScene();

                // Specifies the modality for new window.
                stage.initModality(Modality.WINDOW_MODAL);


                stage.show();
            } catch (IOException e) {
                exceptionAlert("Can not open CreateRoom.fxml");
            }
        }
    }


//    public void setUpCard(){
//        ImageView cardOne = new ImageView(new Image(getClass().getResourceAsStream("/card_all/r_0.jpg" )));
//
//        cardOne.relocate(250, Main.HEIGHT - Card.getHeight());
//
//        pane.getChildren().add(cardOne);
//    }

    @Override
    public String toString() {
        return this.getClass().toString();
    }

    @Override
    public void middleCard(String card) {
        Platform.runLater(
                () -> {
                    ImageView cardOne = new ImageView(new Image(getClass().getResourceAsStream(card)));
                    cardOne.relocate(Main.WIDTH/2 , Main.HEIGHT/2 - (Card.getHeight()/2));
                    pane.getChildren().add(cardOne);
                }
        );
    }

    @Override
    public void showMine(ArrayList<String> cards) {
        this.cards = cards;
        Platform.runLater(
                () -> {
                    for (int i = 0; i < cards.size(); i++) {
                        ImageView cardShow = new ImageView(new Image(getClass().getResourceAsStream(cards.get(i))));
                        imageViews.add(cardShow);
                        cardShow.relocate(250 + i * 70, Main.HEIGHT - Card.getHeight());
                        pane.getChildren().add(cardShow);
                    }
                }
        );
    }

    @Override
    public void showOther(int cards) {
        Platform.runLater(
                () -> {
                    for (int i = 0; i <= cards; i++) {
                        ImageView cardShow = new ImageView(new Image(getClass().getResourceAsStream("/card_all/back.jpg" )));
                        cardShow.relocate(250 + i * 70, 0);
                        pane.getChildren().add(cardShow);
                    }
                }
        );
    }

}
