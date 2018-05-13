package card;

import java.io.Serializable;

public class NumberCard extends Card implements Serializable {

     public NumberCard(String color, String cardValue, int cardPoint) {
        super(color, cardValue, cardPoint);
    }
}
