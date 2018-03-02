package com.example.cs340.tickettoride.Views;

import com.example.cs340.tickettoride.R;

import common.ICard;
import common.PlayerAttributes;
import common.TrainCard;

/**
 * Created by Joseph on 2/28/2018.
 */

public class ColorUtility
{
    public static class CardColors
    {
        CardColors(int backColor, int textColor)
        {
            this.backColor = backColor;
            this.textColor = textColor;
        }
        public int backColor = R.color.colorGrey;
        public int textColor = R.color.colorBlack;
    }

    public static CardColors getColorsFromCard(ICard card)
    {
        if (card.getClass() == TrainCard.class)
        {
            TrainCard tCard = (TrainCard) card;
            TrainCard.Colors tColor = tCard.getColor();
            switch (tColor)
            {
                case red:
                    return new CardColors(R.color.colorRed, R.color.colorWhite);
                case green:
                    return new CardColors(R.color.colorGreen, R.color.colorBlack);
                case blue:
                    return new CardColors(R.color.colorBlue, R.color.colorWhite);
                case white:
                    return new CardColors(R.color.colorWhite, R.color.colorBlack);
                case black:
                    return new CardColors(R.color.colorBlack, R.color.colorWhite);
                case purple:
                    return new CardColors(R.color.colorPurple, R.color.colorWhite);
                case orange:
                    return new CardColors(R.color.colorOrange, R.color.colorBlack);
                case yellow:
                    return new CardColors(R.color.colorYellow, R.color.colorBlack);
                case wildcard:
                    return new CardColors(R.color.colorPink, R.color.colorBlack);
                default:
                    return new CardColors(R.color.colorGrey, R.color.colorBlack);
            }
        }
        return  new CardColors(R.color.colorGrey, R.color.colorBlack);
    }

    public static int getColorFromPlayer(PlayerAttributes.Color color)
    {
        switch (color)
        {
            case red:
                return R.color.colorRed;
            case green:
                return R.color.colorGreen;
            case blue:
                return R.color.colorBlue;
            case black:
                return R.color.colorBlack;
            case yellow:
                return R.color.colorYellow;
            default:
                return R.color.colorGrey;
        }
    }
}
