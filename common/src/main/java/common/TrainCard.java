package common;

/**
 * Created by ephraimkunz on 2/21/18.
 */

public class TrainCard implements ICard {
    public enum TrainColors {BLACK, WHITE, RED, GREEN, BLUE, YELLOW, PINK, ORANGE, WILD}
    private TrainColors color;

    public TrainCard(TrainColors color)
    {
        this.color = color;
    }

    public TrainColors getColor()
    {
        return color;
    }
}
