package model;

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

    public Room(String roomName){
        this.roomName = roomName;
        roomNumber = Server.getRoomNumber();
    }


    public boolean isFull(){
        if(players.size() == MAX_PLAYER) return true;

        return false;
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

    public void addAnotherPlayer(ConnectionToClient client){

    }
}
