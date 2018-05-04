package card;


public class WildCard extends Card {

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
