package card;

import javafx.scene.image.Image;

public interface CardInterface {

    Image getCardImage();
    String getCardImageString();
    String getColor();

    String getValue();

    void setCardPoint(int cardPoint);
    int getCardPoint();
}
