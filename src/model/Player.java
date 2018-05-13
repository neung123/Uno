package model;

import card.Card;

import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card> playerCards;
    private int score = 0;
    private boolean isTurn = false;
    private boolean sayUno = false;

    public Player(String name){
        this.name = name;
        playerCards = new ArrayList<Card>();
    }

    public String getName(){
        return name;
    }

    public void obtainCard(Card card){
        playerCards.add(card);
    }

    public void removeCard(Card card){
        playerCards.remove(card);
    }

    public  ArrayList<Card> getAllCards(){
        return playerCards;
    }

    public int getTotalCards(){
        return playerCards.size();
    }

    public boolean hasCard(Card card){
        return playerCards.contains(card);
    }


    public boolean isTurn(){
        return isTurn;
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


}
