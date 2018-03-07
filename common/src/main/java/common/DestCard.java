package common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ephraimkunz on 2/21/18.
 */

public class DestCard implements ICard {
    private String startCity;
    private String endCity;
    private int pointValue;

    @JsonCreator
    public DestCard(@JsonProperty("startCity") String startCity, @JsonProperty("endCity") String endCity, @JsonProperty("pointValue") int pointValue) {
        this.startCity = startCity;
        this.endCity = endCity;
        this.pointValue = pointValue;
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof DestCard){
            DestCard tempCard = (DestCard)obj;

            if (toString().equals(tempCard.toString())){
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    @Override
    public String toString(){
        return "Start:\t" + startCity + "\n" + "End:\t" + endCity + "\n" + "Points:\t" + pointValue;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
