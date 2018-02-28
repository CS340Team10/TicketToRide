package com.example.cs340.tickettoride.Views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cs340.tickettoride.R;

import java.util.List;

import common.DestCard;
import common.ICard;
import common.TrainCard;

/**
 * Created by Joseph on 2/28/2018.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>
{
    private List<ICard> cards;
    public CardAdapter(List<ICard> cards)
    {
        this.cards = cards;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.card_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ICard card = cards.get(position);
        String text = card.toString();
        CardColors colors = getColorsFromCard(card);
        holder.setText(text);
        holder.setBackColor(colors.backColor);
        holder.setTextColor(colors.textColor);
    }

    private class CardColors
    {
        CardColors(int backColor, int textColor)
        {
            this.backColor = backColor;
            this.textColor = textColor;
        }
        private int backColor = R.color.colorGrey;
        private int textColor = R.color.colorBlack;
    }

    private CardColors getColorsFromCard(ICard card)
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
                case none:
                    return new CardColors(R.color.colorGrey, R.color.colorBlack);
                default:
                    return new CardColors(R.color.colorGrey, R.color.colorBlack);
            }
        }
        return  new CardColors(R.color.colorGrey, R.color.colorBlack);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private View cardCell;
        private TextView cardText;
        public ViewHolder(View itemView) {
            super(itemView);
            this.cardCell = itemView.findViewById(R.id.cardCell);
            this.cardText = itemView.findViewById(R.id.cardText);
        }
        public void setText(String s)
        {
            cardText.setText(s);
        }
        public void setBackColor(int color)
        {
            cardCell.setBackgroundColor(color);
        }
        public void setTextColor(int color)
        {
            cardText.setTextColor(color);
        }
        public void  setBackground(Drawable background)
        {
            cardCell.setBackground(background);
        }
    }
}
