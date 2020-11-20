package war.of.cards;

import java.util.Random;

public class Deck extends Card {

    private final Card[] cards;
    private int top;

    public Deck() {
        cards = new Card[Card.getTypes().length * (Card.getMaxRank() - Card.getMinRank() + 1)];
        refresh();
    }

    public void refresh() {
        Card.Type[] types = Card.getTypes();
        int min_rank = Card.getMinRank();
        int max_rank = Card.getMaxRank();

        int i = 0;
        for (Card.Type type : types)
            for (int rank = min_rank; rank <= max_rank; rank++)
                cards[i++] = new Card(rank, type);

        top = cards.length - 1;
        assert cards[top] != null;
    }

    public void shuffle() {

        Random rng = new Random();

        for (int i = cards.length - 1; i > 0; i--) {
            // Swap the i-th card with a random one
            int j = rng.nextInt(i + 1);
            Card tmp = cards[j];
            cards[j] = cards[i];
            cards[i] = tmp;
        }
    }

    public boolean empty() {
        return top < 0;
    }

    public Card takeCard() {
        if (empty())
            throw new IllegalStateException("Can't deal from an empty deck.");
        return cards[top--];
    }

    public void print() {
        if (empty()) {
            System.out.println("The deck is empty.");
            return;
        }

    }
}



