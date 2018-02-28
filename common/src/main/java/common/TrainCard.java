package common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ephraimkunz on 2/21/18.
 */

public class TrainCard implements ICard {
    public enum Colors {
        red,
        orange,
        yellow,
        green,
        blue,
        purple,
        black,
        white,
        wildcard,
        none // Empty train card
    }

    private Colors color;

    @JsonCreator
    public TrainCard(@JsonProperty("color") Colors color) {
        this.color = color;
    }

    public Colors getColor() {
        return color;
    }

    @Override
    public String toString(){
        return color.toString();
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof TrainCard){
            TrainCard card = (TrainCard)obj;

            if (card.toString().equals(toString())){
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
}
