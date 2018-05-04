package card;

public enum CardType {
    ZERO(0),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    WILDCARD(50),
    ACTIONCARD(20);

    private final int cardPoint;

    CardType(int cardPoint) { this.cardPoint = cardPoint; }

    public int getCardPoint() {
        return cardPoint;
    }
}
