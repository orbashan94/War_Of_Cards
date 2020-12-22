package war.of.cards.objects;


import java.util.Objects;

public class Card {

    public enum Type {
        HEARTS, DIAMONDS, SPADES, CLUBS;
    }

    private static final int MIN_RANK = 2;
    private static final int MAX_RANK = 14;

    private int rank;
    private Type type;

    public Card() {
    }

    public Card(int rank, Type type) {
        setRank(rank);
        setType(type);
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        if (rank < MIN_RANK || rank > MAX_RANK)
            throw new RuntimeException(
                    String.format("Invalid rank: %d (must be between %d and %d inclusive)",
                            rank, MIN_RANK, MAX_RANK));
        this.rank = rank;
    }


    public Type getType() {
        return type;
    }


    public void setType(Type type) {
        if (type == null)
            throw new RuntimeException("Type must be non-null");
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("%s[rank=%d, suit=%s]",
                getClass().getSimpleName(),
                getRank(),
                getType().name());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Card))
            return false;
        if (obj == this)
            return true;

        Card that = (Card) obj;
        return that.getRank() == getRank() && that.getType() == getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRank(), getType());
    }

    public static int getMinRank() {
        return MIN_RANK;
    }

    public static int getMaxRank() {
        return MAX_RANK;
    }

    public static Type[] getTypes() {
        return Type.values();
    }

}

