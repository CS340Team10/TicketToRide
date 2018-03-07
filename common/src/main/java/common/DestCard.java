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
    public String toString(){
        return "Start:\t" + startCity + "\n" + "End:\t" + endCity + "\n" + "Points:\t" + pointValue;
    }

    @Override
    public boolean equals(Object o)
    {
        if (getClass() == o.getClass())
        {
            return (toString().equals(o.toString()));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
