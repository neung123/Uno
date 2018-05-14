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

    @Override
    public String toString() {
        return String.format("Card is:%s %s",getColor(),getValue());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj.getClass() != getClass()) return false;

        Card other = (Card) obj;

        return (this.getColor().equals(other.color)) && (this.getValue().equals(other.cardValue));
    }
}
