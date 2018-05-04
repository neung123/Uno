package display.client;

import card.Card;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class InGameController {
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


}
