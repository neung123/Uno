package model;

import card.Card;
import network.Server;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable{
    private String name;
    private ArrayList<Card> playerCards;
    private boolean isTurn = false;
    private boolean sayUno = false;
    private final int ID;

    public Player(String name,int ID){
        this.name = name;
        playerCards = new ArrayList<Card>();
        this.ID = ID;
    }

    public String getName(){
        return name;
    }

    public void obtainCard(Card card){
        playerCards.add(card);
    }

    public void removeCard(Card card){
        playerCards.remove(card);
        System.out.println("remove:" + card);
        System.out.println("current is:" + playerCards);
    }

    public  ArrayList<Card> getAllCards(){
        return playerCards;
    }

    public  ArrayList<String> getAllCardsString(){
        ArrayList<String> temp = new ArrayList<>();

        for(Card c: playerCards) temp.add(c.getCardImageString());
        System.out.println("get string " + playerCards);

        return temp;
    }

    public int getTotalCards(){
        return playerCards.size();
    }

    public boolean hasCards(){
        return getTotalCards() <= 0;
    }

    public boolean hasCard(Card card){
        return playerCards.contains(card);
    }

    public boolean isTurn(){
        System.out.println(getName() + getID() + isTurn);
        return isTurn;
    }

    public void setTurn(boolean turn) {
        isTurn = turn;
    }

    public boolean getSaidUNO(){
        return sayUno;
    }

    public void sayUNO(){
         sayUno = true;
    }

    public void setSayUNOFalse(){
        sayUno = false;
    }

    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        return getName() + getID();
    }
}
