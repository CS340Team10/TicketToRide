package common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ephraimkunz on 3/13/18.
 */

public class PlayerPointSummary {

    private static final int LONGEST_ROUTE_BONUS = 10;

    private String playerId;
    private int claimedRoutePoints;
    private int destCardPoints;
    private int longestRoutePoints;
    private boolean isWinner;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getClaimedRoutePoints() {
        return claimedRoutePoints;
    }

    public void setClaimedRoutePoints(int claimedRoutePoints) {
        this.claimedRoutePoints = claimedRoutePoints;
    }

    public int getDestCardPoints() {
        return destCardPoints;
    }

    public void setDestCardPoints(int destCardPoints) {
        this.destCardPoints = destCardPoints;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }

    @JsonCreator
    public PlayerPointSummary(@JsonProperty("playerId") String playerId, @JsonProperty("claimedRoutePoints") int claimedRoutePoints, @JsonProperty("destCardPoints") int destCardPoints, @JsonProperty("longestRoutePoints") int longestRoutePoints, @JsonProperty("isWinner") boolean isWinner) {
        this.playerId = playerId;
        this.claimedRoutePoints = claimedRoutePoints;
        this.destCardPoints = destCardPoints;
        this.longestRoutePoints = longestRoutePoints;
        this.isWinner = isWinner;
    }

    public void applyLongestPathBonus(){
        this.longestRoutePoints = LONGEST_ROUTE_BONUS;
    }

    /**
     * Sets the points for the longest path after the Object has been created
     *
     * @param newPoints the longest path bonus to be added
     */
    public void setLongestRoutePoints(int newPoints){
        this.longestRoutePoints = newPoints;
    }

    /**
     * Returns the longest route points
     * @return
     */
    public int getLongestRoutePoints(){
        return longestRoutePoints;
    }

    /**
     * Sets whether the Player represented by this Object is the winner
     *
     * @param isWinner true if this Player won, false otherwise
     */
    public void setIsWinner(boolean isWinner){
        this.isWinner = isWinner;
    }

    /**
     * Returns the total points earned
     *
     * @return the total number of points earned
     */
    @JsonIgnore
    public int getTotalPoints() {
        return claimedRoutePoints + destCardPoints + longestRoutePoints;
    }
}
