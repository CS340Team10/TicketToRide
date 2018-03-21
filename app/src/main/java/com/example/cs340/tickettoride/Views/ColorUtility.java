package com.example.cs340.tickettoride.Views;


import com.example.cs340.tickettoride.R;

import ClientModel.Player;

import common.ICard;
import common.PlayerAttributes;
import common.Route;
import common.TrainCard;

/**
 * Created by Joseph on 2/28/2018.
 */

public class ColorUtility
{
    public static final int colorWhite=0xFFFFFFFF;
    public static final int colorBlack = 0xFF000000;
    public static final int colorGrey = 0xFFAAAAAA;
    public static final int colorRed = 0xFFFF0000;
    public static final int colorGreen = 0xFF00FF00;
    public static final int colorBlue = 0xFF0000FF;
    public static final int colorPurple = 0xFFFF00FF;
    public static final int colorOrange = 0xFFFF9900;
    public static final int colorYellow = 0xFFCCCC00;
    public static final int colorPink = 0xFFFFBBAA;

    public static class BiColorContrastPalette
    {
        BiColorContrastPalette(int backColor, int textColor)
        {
            this.backColor = backColor;
            this.textColor = textColor;
        }
        public int backColor = colorGrey;
        public int textColor = colorWhite;
    }

    public static BiColorContrastPalette getColorsFromCard(ICard card)
    {
        if (card.getClass() == TrainCard.class)
        {
            TrainCard tCard = (TrainCard) card;
            TrainCard.Colors tColor = tCard.getColor();
            switch (tColor)
            {
                case red:
                    return new BiColorContrastPalette(colorRed, colorWhite);
                case green:
                    return new BiColorContrastPalette(colorGreen, colorBlack);
                case blue:
                    return new BiColorContrastPalette(colorBlue, colorWhite);
                case white:
                    return new BiColorContrastPalette(colorWhite, colorBlack);
                case black:
                    return new BiColorContrastPalette(colorBlack, colorWhite);
                case purple:
                    return new BiColorContrastPalette(colorPurple, colorWhite);
                case orange:
                    return new BiColorContrastPalette(colorOrange, colorBlack);
                case yellow:
                    return new BiColorContrastPalette(colorYellow, colorBlack);
                case wildcard:
                    return new BiColorContrastPalette(colorPink, colorBlack);
                default:
                    return new BiColorContrastPalette(colorGrey, colorWhite);
            }
        }
        return  new BiColorContrastPalette(colorWhite, colorBlack);
    }

    public static int getColorFromPlayer(PlayerAttributes.Color color)
    {
        switch (color)
        {
            case red:
                return colorRed;
            case green:
                return colorGreen;
            case blue:
                return colorBlue;
            case black:
                return colorBlack;
            case yellow:
                return colorYellow;
            default:
                return colorGrey;
        }
    }

    public static int getColorFromRoute(Route route)
    {
        switch (route.getPathColor())
        {
            case red:
                return colorRed;
            case green:
                return colorGreen;
            case blue:
                return colorBlue;
            case white:
                return colorWhite;
            case black:
                return colorBlack;
            case purple:
                return colorPurple;
            case orange:
                return colorOrange;
            case yellow:
                return colorYellow;
            case wildcard:
                return colorPink;
            default:
                return colorGrey;
        }
    }

    public static BiColorContrastPalette getColorsFromRoute(Route route)
    {
        switch (route.getPathColor())
        {
            case red:
                return new BiColorContrastPalette(colorRed, colorWhite);
            case green:
                return new BiColorContrastPalette(colorGreen, colorBlack);
            case blue:
                return new BiColorContrastPalette(colorBlue, colorWhite);
            case white:
                return new BiColorContrastPalette(colorWhite, colorBlack);
            case black:
                return new BiColorContrastPalette(colorBlack, colorWhite);
            case purple:
                return new BiColorContrastPalette(colorPurple, colorWhite);
            case orange:
                return new BiColorContrastPalette(colorOrange, colorBlack);
            case yellow:
                return new BiColorContrastPalette(colorYellow, colorBlack);
            case wildcard:
                return new BiColorContrastPalette(colorPink, colorBlack);
            default:
                return new BiColorContrastPalette(colorGrey, colorWhite);
        }
    }

    public static int modifyColorAlpha(int newAlpha, int color)
    {
        if (newAlpha > 0xFF)
        {
            newAlpha = 0xFF;
        }
        return (newAlpha << 6) & color;
    }

    public static int getColorFromPlayer(Player.PlayerColors color)
    {
        switch (color)
        {
            case red:
                return colorRed;
            case green:
                return colorGreen;
            case blue:
                return colorBlue;
            case black:
                return colorGrey;
            case yellow:
                return colorYellow;
            default:
                return colorGrey;
        }
    }
}
