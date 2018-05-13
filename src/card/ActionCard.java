package card;

import java.io.Serializable;

public class ActionCard extends Card implements Serializable {
    public ActionCard(String color, String cardValue) {
        super(color, cardValue, 20);
    }
}
