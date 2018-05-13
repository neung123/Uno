package card;

import javafx.scene.image.Image;

import java.io.Serializable;

public class Card implements CardInterface,Serializable {
    String color = null;
    String cardValue = null;
    int cardPoint = 0;
    private static final int WIDTH = 140;
    private static final int HEIGHT = 200;

    public Card(String color,String cardValue,int cardPoint){
        this.color = color;
        this.cardValue = cardValue;
        this.cardPoint = cardPoint;
    }



    @Override
    public Image getCardImage() {
        String card = color.charAt(0) + "_" + cardValue;
        return new Image(getClass().getResourceAsStream("/card_all/" + card ));
    }

    @Override
    public String getCardImageString() {
        String card = color.charAt(0) + " " + cardValue;
        return card;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public String getValue() {
        return cardValue;
    }

    @Override
    public void setCardPoint(int cardPoint) {
        this.cardPoint = cardPoint;
    }

    @Override
    public int getCardPoint() {
        return cardPoint;
    }


    public static int getWidth() {
        return WIDTH;
    }

    public static int getHeight() {
        return HEIGHT;
    }
}
