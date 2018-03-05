package com.example.cs340.tickettoride.Views;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.cs340.tickettoride.R;

import java.util.ArrayList;
import java.util.List;

import common.ICard;
import common.Route;
import common.TrainCard;

/**
 * Created by Joseph on March 5, 2018
 */
public class ClaimRouteDialog extends DialogFragment
{
    //IClaimRouteView claimRouteView;
    RecyclerView claimRouteRecycler = null;
    RecyclerView trainNumberRecycler = null;
    Button submitButton;

    public ClaimRouteDialog() {
        // Required empty public constructor
    }

    public static ClaimRouteDialog newInstance(/*IClaimRouteView claimRouteView*/)
    {
        ClaimRouteDialog dialog = new ClaimRouteDialog();
        //dialog.claimRouteView = claimRouteView;
        return dialog;
    }

    public void updateRouteList(List<Route> routeList)
    {
        if (claimRouteRecycler != null)
        {
            claimRouteRecycler.setAdapter(new ClaimRouteAdapter(routeList));
        }
    }

    public void updateCardCountList(List<Pair<ICard, Integer>> cardNumList)
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_claim_route_dialog, container, false);
        claimRouteRecycler = v.findViewById(R.id.claimRouteRecycler);
        trainNumberRecycler = v.findViewById(R.id.trainNumberRecycler);
        submitButton = v.findViewById(R.id.submitClaimRouteBtn);
        //submitButton.setEnabled(false);
        return v;
    }

    private class ClaimRouteAdapter extends RecyclerView.Adapter<ClaimRouteViewHolder>
    {
        List<Route> routeList;

        public ClaimRouteAdapter(List<Route> routeList)
        {
            this.routeList = routeList;
        }

        @NonNull
        @Override
        public ClaimRouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.game_list_item, parent, false);
            return new ClaimRouteViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ClaimRouteViewHolder holder, int position) {
            holder.mTextView.setText(routeList.get(position).toString());
        }

        @Override
        public int getItemCount() {
            return routeList.size();
        }
    }

    private class ClaimRouteViewHolder extends RecyclerView.ViewHolder
    {
        TextView mTextView;

        public ClaimRouteViewHolder(View itemView)
        {
            super(itemView);
            mTextView = itemView.findViewById(R.id.gameItemTitle);
        }
    }

    private class TrainNumberAdapter extends RecyclerView.Adapter<TrainNumberViewHolder>
    {
        List<Pair<ICard, Integer>> cardNumList;

        public TrainNumberAdapter(List<Pair<ICard, Integer>> cardNumList)
        {
            this.cardNumList = cardNumList;
        }

        @NonNull
        @Override
        public TrainNumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.train_card_number_item, parent, false);
            return new TrainNumberViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull TrainNumberViewHolder holder, int position) {
            ICard card = cardNumList.get(position).first;
            Integer num = cardNumList.get(position).second;
            String txt;
            if (card == null || num == null)
            {
                num = 0;
                txt = "";
            }
            else
            {
                txt = card.toString() + "(" + num.toString() + ")";
            }
            holder.mTextView.setText(txt);
            holder.mPicker.setMaxValue(num);
            holder.mPicker.setMinValue(0);
        }

        @Override
        public int getItemCount() {
            return cardNumList.size();
        }
    }

    private class TrainNumberViewHolder extends RecyclerView.ViewHolder
    {
        TextView mTextView;
        NumberPicker mPicker;

        public TrainNumberViewHolder(View itemView)
        {
            super(itemView);
            mTextView = itemView.findViewById(R.id.trainCardCount);
            mPicker = itemView.findViewById(R.id.trainNumberPicker);
        }
    }
}
