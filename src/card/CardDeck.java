package card;
/**
 * Standard 108 card deck
 *
 * @author Pornpavee Seri-umnuoy
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class CardDeck {

    public ArrayList<Card> defaultDeck = new ArrayList<>();
    public Stack<Card> shuffleDeck = new Stack<>();

    String[] UNO_COLORS = {"red", "blue", "green", "yellow"};
    String[] ActionTypes = {"reverse", "+2", "skip"};
    String[] WildTypes = {"0", "+4"};
    int[] UNO_NUMBERS =  {0,1,2,3,4,5,6,7,8,9};

    public CardDeck(){
        addCards();
        addToStack();
    }

    public void addToStack(){
        ArrayList<Card> cards = new ArrayList<>(defaultDeck);

        //shuffle 3 times
        for(int i = 1; i<= 3; i++) Collections.shuffle(cards);

        for (Card c: cards) shuffleDeck.add(c);
    }

    public Card getCard(){
        if(shuffleDeck.empty()) addToStack();
        return shuffleDeck.pop();
    }

    public Stack<Card> getAllCards() {
        return shuffleDeck;
    }

    private void addCards() {
        for(String color:UNO_COLORS){

            //Create 76 NumberCards --> doubles except 0s.
            for(int num : UNO_NUMBERS){
                int i=0;
                do{
                    defaultDeck.add(new NumberCard(color, Integer.toString(num), num));
                    i++;
                }while(num!=0 && i<2);
            }

            //Create 24 ActionCards --> everything twice
            for(String type : ActionTypes){
                for(int i=0;i<2;i++)
                    defaultDeck.add(new ActionCard(color, type));
            }
        }

        for(String type : WildTypes){
            for(int i=0;i<4;i++){
                defaultDeck.add(new WildCard(type));
            }
        }

    }

    public int getTotalCard() {
        return shuffleDeck.size();
    }
}
