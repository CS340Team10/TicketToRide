package ClientModel;

import java.util.ArrayList;
import java.util.List;

import common.TrainCard;

/**
 * Created by Joseph on 2/2/2018.
 */

public class Game
{
    private boolean didStart = false;
    private ArrayList<Player> players = new ArrayList<>();
    private int destCardDeckNum = 0;
    private int trainCardDeckNum = 0;
    ArrayList<TrainCard> faceupTrainCards = new ArrayList<>();
    public Game()
    {

    }

    /**
     *
     * @return whether the game has already started or not
     */
    public boolean hasStarted()
    {
        return didStart;
    }

    /**
     * If there are enough players (greater than or equal to the required player count)
     * tell the game that it has started
     * But if there are not enough players, the game will not start and the method will return false
     * @return whether the game successfully started
     */
    public void startGame()
    {
        didStart = true;
    }

    public ArrayList<Player> getPlayers()
    {
        return players;
    }

    public int getDestCardDeckNum() {
        return destCardDeckNum;
    }

    public void setDestCardDeckNum(int destCardDeckNum) {
        this.destCardDeckNum = destCardDeckNum;
    }

    public int getTrainCardDeckNum() {
        return trainCardDeckNum;
    }

    public void setTrainCardDeckNum(int trainCardDeckNum) {
        this.trainCardDeckNum = trainCardDeckNum;
    }

    public ArrayList<TrainCard> getFaceupTrainCards() {
        return faceupTrainCards;
    }

    public void setFaceupTrainCards(ArrayList<TrainCard> faceupTrainCards) {
        this.faceupTrainCards = faceupTrainCards;
    }

}
