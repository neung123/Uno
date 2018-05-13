package model;

import card.Card;
import card.CardDeck;

import java.io.Serializable;
import java.util.ArrayList;

public class Dealer implements Serializable {
    public final int START_CARD_FOR_PLAYER = 7;

    private CardDeck cardDeck;

    public Dealer() {
        cardDeck = new CardDeck();
    }


    //Spread cards to players - 7 each
    public void spreadOut(ArrayList<Player> players) {

        for (int i = 1; i <= START_CARD_FOR_PLAYER; i++) {
            for (Player p : players) p.obtainCard(cardDeck.getCard());
        }
    }

    public Card getCard() {
        return cardDeck.getCard();
    }

}
