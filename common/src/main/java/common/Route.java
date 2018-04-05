package common;

import java.io.Serializable;

/**
 * Created by ephraimkunz on 2/21/18.
 */

public class Route implements Serializable {
    private String routeID;
    private String startCity;
    private String endCity;
    int routeLength;
    TrainCard.Colors pathColor;
    String ownedByPlayerID;

    public Route(String routeID, String startCity, String endCity, int routeLength, TrainCard.Colors pathColor)
    {
        this.routeID = routeID;
        this.startCity = startCity;
        this.endCity = endCity;
        this.routeLength = routeLength;
        this.pathColor = pathColor;
        this.ownedByPlayerID = null;
    }

    public String getRouteID() {
        return routeID;
    }

    public String getStartCity() {
        return startCity;
    }

    public String getEndCity() {
        return endCity;
    }

    public int getRouteLength() {
        return routeLength;
    }

    public TrainCard.Colors getPathColor() {
        return pathColor;
    }

    public String getOwnedByPlayerID() {
        return ownedByPlayerID;
    }

    public void setOwnedByPlayerID(String playerID)
    {
        ownedByPlayerID = playerID;
    }

    public int getPointValue(){
        switch (routeLength){
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 4;
            case 4:
                return 7;
            case 5:
                return 10;
            case 6:
                return 15;
            default:
                return 0;
        }
    }

}
