package card;

public enum CardType {
    NumberCard(0),
    WildCard(50),
    ActionCard(20);

    private final int cardPoint;

    CardType(int cardPoint) { this.cardPoint = cardPoint; }
}
