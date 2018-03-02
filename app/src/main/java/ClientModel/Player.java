package ClientModel;

import common.Deck;

/**
 * Created by Joseph on 2/2/2018.
 */

public class Player
{
    private String id;
    private String username;
    private Deck trainCards;
    private Deck destCards;
    private Deck offeredDestCards;
    private int points;
    private boolean isMyTurn;
    private int historyPosition;
    private int turnOrder;
    public enum PlayerColors{
        yellow,
        red,
        green,
        blue,
        black
    }
    private PlayerColors color;

    public Player() {
        historyPosition = 0; // Start from beginning of history list
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Deck getTrainCards() {
        return trainCards;
    }

    public void setTrainCards(Deck trainCards) {
        this.trainCards = trainCards;
    }

    public Deck getDestCards() {
        return destCards;
    }

    public void setDestCards(Deck destCards) {
        this.destCards = destCards;
    }

    public Deck getOfferedDestCards() {
        return offeredDestCards;
    }

    public void setOfferedDestCards(Deck offeredDestCards) {
        this.offeredDestCards = offeredDestCards;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isMyTurn() {
        return isMyTurn;
    }

    public void setMyTurn(boolean myTurn) {
        isMyTurn = myTurn;
    }

    public int getHistoryPosition() {
        return historyPosition;
    }

    public void setHistoryPosition(int historyPosition) {
        this.historyPosition = historyPosition;
    }

    public int getTurnOrder() {
        return turnOrder;
    }

    public void setTurnOrder(int turnOrder) {
        this.turnOrder = turnOrder;
    }

    public PlayerColors getColor() {
        return color;
    }

    public void setColor(PlayerColors color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() == getClass())
        {
            Player p = (Player) o;
            return (id.equals(p.id));
        }
        return false;
    }
}
