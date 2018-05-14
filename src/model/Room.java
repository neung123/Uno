package model;

import card.Card;
import card.WildCard;
import network.Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;


public class Room implements Serializable {
    public int roomNumber;
    public String roomName;
    Dealer dealer = new Dealer();
    boolean isOver = false;
    public static final int MAX_PLAYER = 2;
    ArrayList<Player> players = new ArrayList<>();
    ArrayList<Card> cards = new ArrayList<>();

    public Room(String roomName){
        this.roomName = roomName;
        roomNumber = Server.getRoomNumber();
        cards.add(dealer.getCard());
    }


    public String getRoomName() {
        return roomName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void start(){
        dealer.spreadOut(players);
    }

    public void addPlayer(Player player){
        if(!isFull()) players.add(player);
    }

    public Player getPlayer1() {
        return players.get(0);
    }

    public Player getPlayer2() {
        return players.get(1);
    }

    public boolean play(String newCard,Player player) {
        Card card = createCard(newCard);


        // Color or value matches
        if (getCurrentCard().getColor().equals(card.getColor()) || getCurrentCard().getValue().equals(card.getValue())) {
            cards.add(card);
            player.removeCard(card);
            return true;
            // if chosen wild card color matches
        } else if (getCurrentCard().getColor().equals("wild")) {
            if( ((WildCard) getCurrentCard()).getWildColor().equals(card.getColor())){
                cards.add(card);
                player.removeCard(card);
                return true;
            }

            // suppose the new card is a wild card
        } else if (card.getColor().equals("wild")) {
            cards.add(card);
            player.removeCard(card);
            return true;
        }

        return false;
    }

    public boolean play(Card newCard,Player player){

        // Color or value matches
        if (getCurrentCard().getColor().equals(newCard.getColor())
                || getCurrentCard().getValue().equals(newCard.getValue())) {
            cards.add(newCard);
            player.removeCard(newCard);
            return true;
            // if chosen wild card color matches
        }else if (getCurrentCard().getColor().equals("wild")) {
            cards.add(newCard);
            player.removeCard(newCard);
            return ((WildCard) getCurrentCard()).getWildColor().equals(newCard.getColor());

            // suppose the new card is a wild card
        }else if (newCard.getColor().equals("wild")) {
            cards.add(newCard);
            player.removeCard(newCard);
            return true;
        }
        // else
        return false;
    }

    public Card createCard(String newCard){
        String[] temp = newCard.split("_");
        String color = null;
        String[] temp1 = temp[1].split("\\.");
        String value = temp1[0];
        switch (temp[0]){
            case "r":
                color = "red";
                break;
            case "g":
                color = "green";
                break;
            case "b":
                color = "blue";
                break;
            case "y":
                color = "yellow";
                break;
            case "w":
                color = "wild";
                break;
        }
        return new Card(color, value, 0);
    }

    public String getCurrentCardString(){
       return cards.get(cards.size() - 1).getCardImageString();
    }

    public Card getCurrentCard(){
        return cards.get(cards.size() - 1);
    }

    public String getCurrentCardValue(){
        return cards.get(cards.size() - 1).getValue();
    }

    public boolean isOver() {
        for (Player p : players) {
            if (!p.hasCards()) {
                isOver = true;
                break;
            }
        }

        return isOver;
    }

    public boolean isFull(){ return players.size() == MAX_PLAYER;}


    @Override
    public String toString() {
        return roomName + roomNumber;
    }
}
