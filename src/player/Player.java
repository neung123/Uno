package player;

import java.util.LinkedList;

public class Player {
    private String name;
    private LinkedList card;
    private boolean isTurn;
    private boolean isSayUno;

    public Player(String name){
        this.name = name;
    }
}
