package card;

import java.awt.*;

public class Card {
    String color = null;
    CardType cardType = null;
    String cardValue = null;
    int cardPoint = 0;

    public Card(String color,CardType cardType,String cardValue,int cardPoint){
        this.color = color;
        this.cardType = cardType;
        this.cardValue = cardValue;
        this.cardPoint = cardPoint;
    }
}
