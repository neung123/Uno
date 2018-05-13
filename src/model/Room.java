package model;

import card.Card;
import network.Server;

import java.io.Serializable;
import java.util.ArrayList;


public class Room implements Serializable {
    public int roomNumber;
    public String roomName;
    Dealer dealer = new Dealer();
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
        players.add(player);
        player.setTurn(true);
    }

    public Player getPlayer1() {
        return players.get(0);
    }

    public Player getPlayer2() {
        return players.get(1);
    }

    public boolean play(String newCard){
//        Card card = Card();
//
//        // Color or value matches
//        if (getCurrentCard().getColor().equals(newCard.getColor())
//                || topCard.getValue().equals(newCard.getValue()))
//            return true;
//            // if chosen wild card color matches
//        else if (topCard.getType() == WILD)
//            return ((WildCard) topCard).getWildColor().equals(newCard.getColor());
//
//            // suppose the new card is a wild card
//        else if (newCard.getType() == WILD)
//            return true;
//
//        // else
        return false;
    }

    public Card createCard(String newCard){

        return null;
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


    @Override
    public String toString() {
        return roomName + roomNumber;
    }
}
