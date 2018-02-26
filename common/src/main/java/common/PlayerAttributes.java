package common;

/**
 * Created by ephraimkunz on 2/21/18.
 */

public class PlayerAttributes {
    public enum Color {
        blue,
        red,
        green,
        yellow,
        black
    }

    // OK for these to be public since it is just a data transfer object.
    public String playerId; // Use to uniquely identify this player
    public String username; // Display this on screen
    public Color color;
    public int trainCardNum;
    public int destCardNum;
    public int points;
}
