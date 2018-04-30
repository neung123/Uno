package display;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import player.Player;

public class InGameController {
    @FXML
    ImageView imageViewBG;
    @FXML
    Button placeButton;
    @FXML
    Button drawButton;
    @FXML
    Button unoButton;

    Player player;



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


    }


}
