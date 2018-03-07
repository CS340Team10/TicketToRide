package com.example.cs340.tickettoride.Views;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cs340.tickettoride.R;

import java.util.List;

import common.ICard;

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
        ColorUtility.BiColorContrastPalette colors = ColorUtility.getColorsFromCard(card);
        holder.setText(text);
        holder.setBackColor(colors.backColor);
        holder.setTextColor(colors.textColor);
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
