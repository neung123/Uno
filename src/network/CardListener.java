package network;

import card.Card;

import java.util.ArrayList;

public interface CardListener {
    void middleCard(String card);
    void showMine(ArrayList<String> cards);
    void showOther(int cards);

}
