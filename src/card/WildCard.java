package card;


import java.io.Serializable;

public class WildCard extends Card implements Serializable {

    private String chosenColor;

    public WildCard(String cardValue) {
        super("wild", cardValue,50);
    }

    public void useWildColor(String chosenColor){
        this.chosenColor = chosenColor;
    }

    public String getWildColor(){
        return chosenColor;
    }
}
