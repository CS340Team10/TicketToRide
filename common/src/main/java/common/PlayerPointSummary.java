package common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ephraimkunz on 3/13/18.
 */

public class PlayerPointSummary {
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

    public int getLongestRoutePoints() {
        return longestRoutePoints;
    }

    public void setLongestRoutePoints(int longestRoutePoints) {
        this.longestRoutePoints = longestRoutePoints;
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

    public int getTotalPoints() {
        return claimedRoutePoints + destCardPoints + longestRoutePoints;
    }
}
