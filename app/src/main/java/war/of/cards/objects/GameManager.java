package war.of.cards.objects;

public class GameManager {

    private Deck deck;
    private Card playerACard;
    private Card playerBCard;
    private int scorePlayerA = 0;
    private int scorePlayerB = 0;
    private String winner;
    private int scoreWinner = 0;

    public GameManager() {
        deck = new Deck();
        deck.shuffle();
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setPlayerACard(Card playerACard) {
        this.playerACard = playerACard;
    }

    public Card getPlayerACard() {
        return playerACard;
    }

    public void setPlayerBCard(Card playerBCard) {
        this.playerBCard = playerBCard;
    }

    public Card getPlayerBCard() {
        return playerBCard;
    }

    public void setScorePlayerA(int scorePlayerA) {
        this.scorePlayerA = scorePlayerA;
    }

    public int getScorePlayerA() {
        return scorePlayerA;
    }

    public void setScorePlayerB(int scorePlayerB) {
        this.scorePlayerB = scorePlayerB;
    }

    public int getScorePlayerB() {
        return scorePlayerB;
    }

    public void setScoreWinner(int scoreWinner) {
        this.scoreWinner = scoreWinner;
    }

    public int getScoreWinner() {
        return scoreWinner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getWinner() {
        return winner;
    }


    public void endGame() {
        if (getScorePlayerA() > getScorePlayerB()) {
            setWinner("Player A");
            setScoreWinner(getScorePlayerA());
        } else if (getScorePlayerB() > getScorePlayerA()) {
            setWinner("Player B");
            setScoreWinner(getScorePlayerB());
        } else {
            setWinner("Draw");
            setScoreWinner(getScorePlayerA());
        }


    }

    public void calcScore() {
        if (playerACard.getRank() > playerBCard.getRank()) {
            scorePlayerA++;
        } else if (playerBCard.getRank() > playerACard.getRank()) {
            scorePlayerB++;
        }
    }
}

