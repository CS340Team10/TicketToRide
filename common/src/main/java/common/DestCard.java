package common;

/**
 * Created by ephraimkunz on 2/21/18.
 */

public class DestCard implements ICard {
    private String startCity;
    private String endCity;
    private int pointValue;

    public DestCard(String start, String end, int value)
    {
        this.startCity = start;
        this.endCity = end;
        this.pointValue = value;
    }

    public String getStartCity() {
        return startCity;
    }

    public String getEndCity() {
        return endCity;
    }

    public int getPointValue() {
        return pointValue;
    }

}
