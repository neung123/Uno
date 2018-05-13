package model;

import card.Card;
import network.CardListener;
import network.Client;
import network.Server;
import server.ConnectionToClient;

import java.util.ArrayList;


public class Room {
    public int roomNumber;
    public String roomName;
    Dealer dealer = new Dealer();
    public static final int MAX_PLAYER = 2;
    ArrayList<Player> players = new ArrayList<>();
    CardListener listener;

    public Room(String roomName){
        this.roomName = roomName;
        roomNumber = Server.getRoomNumber();
    }


    public String getRoomName() {
        return roomName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void start(){
        dealer.spreadOut(players);
//        listener.middleCard(dealer.getCard());
    }

    public void addPlayer(Player player){
        players.add(player);

        System.out.println(player);
    }

    public Player getPlayer1() {
        System.out.println();
        return players.get(0);
    }

    public Player getPlayer2() {
        return players.get(1);
    }

    public void setListener(CardListener listener) {
        this.listener = listener;
    }

    public void currentCard(){
        listener.middleCard(dealer.getCard());
    }
}
