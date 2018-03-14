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
