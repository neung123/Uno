package card;

import java.awt.*;

public interface CardInterface{

    int WIDTH = 141;
    int HEIGHT = 200;

    //Default card size
    Dimension CARDSIZE = new Dimension(WIDTH,HEIGHT);


    void setColor(String newColor);
    String getColor();

    void setValue(String newValue);
    String getValue();

    void setType(CardType newType);
    CardType getType();

    void setCardPoint(int cardPoint);
    int getCardPoint();
}
