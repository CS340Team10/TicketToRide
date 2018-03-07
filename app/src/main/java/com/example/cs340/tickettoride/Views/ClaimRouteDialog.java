package com.example.cs340.tickettoride.Views;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.cs340.tickettoride.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    List<Route> routes = new ArrayList<>();
    Map<ICard, Integer> availableCards = new HashMap<>();

    public ClaimRouteDialog() {
        // Required empty public constructor
    }

    public static ClaimRouteDialog newInstance(List<Route> routes, Map<ICard, Integer> availableCards)
    {
        ClaimRouteDialog dialog = new ClaimRouteDialog();
        dialog.routes = routes;
        dialog.availableCards = availableCards;
        return dialog;
    }

    public void updateRouteList(List<Route> routeList)
    {
        if (claimRouteRecycler != null)
        {
            claimRouteRecycler.setAdapter(new ClaimRouteAdapter(routeList));
        }
    }

    public void updateCardCountList(Map<ICard, Integer> cardAndNums)
    {
        if (trainNumberRecycler != null)
        {
            List<Pair<ICard, Integer>> cardNumList = new ArrayList<>();
            Set<ICard> cards = cardAndNums.keySet();
            for (ICard card : cards)
            {
                Integer cnt = cardAndNums.get(card);
                cardNumList.add(new Pair<>(card, cnt));
            }
            trainNumberRecycler.setAdapter(new TrainNumberAdapter(cardNumList));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_claim_route_dialog, container, false);
        claimRouteRecycler = v.findViewById(R.id.claimRouteRecycler);
        LinearLayoutManager claimLayoutManager = new LinearLayoutManager(getContext());
        claimLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        claimRouteRecycler.setLayoutManager(claimLayoutManager);
        claimRouteRecycler.setAdapter(new ClaimRouteAdapter(new ArrayList<Route>()));
        trainNumberRecycler = v.findViewById(R.id.trainNumberRecycler);
        LinearLayoutManager trainLayoutManager = new LinearLayoutManager(getContext());
        trainLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        trainNumberRecycler.setLayoutManager(trainLayoutManager);
        trainNumberRecycler.setAdapter(new TrainNumberAdapter(new ArrayList<Pair<ICard, Integer>>()));
        submitButton = v.findViewById(R.id.submitClaimRouteBtn);
        //submitButton.setEnabled(false);
        updateRouteList(routes);
        updateCardCountList(availableCards);
        return v;
    }

    private class ClaimRouteAdapter extends RecyclerView.Adapter<ClaimRouteViewHolder>
    {
        List<Route> routeList;
        int selectedPosition = 0;
        RadioButton selectedButton;

        public ClaimRouteAdapter(List<Route> routeList)
        {
            this.routeList = routeList;
        }

        @NonNull
        @Override
        public ClaimRouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.route_list_item, parent, false);
            ClaimRouteViewHolder holder = new ClaimRouteViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ClaimRouteViewHolder holder, int position)
        {
            String routeText;
            Route route = routeList.get(position);
            routeText = route.getStartCity()+" to "+route.getEndCity()+"\n"
                    +"Length: "+Integer.toString(route.getRouteLength())+"\n";
            holder.mTextView.setText(routeText);
            int textColor = ColorUtility.getColorsFromRoute(route).textColor;
            int backColor = ColorUtility.getColorsFromRoute(route).backColor;
            holder.mTextView.setBackgroundColor(backColor);
            holder.mTextView.setTextColor(textColor);
            holder.mRoute = route;
            if (selectedButton == null)
            {
                selectedButton = holder.mRadioButton;
                selectedPosition = position;
                selectedButton.setChecked(true);
            }
            final int pos = position;
            final ClaimRouteViewHolder hldr = holder;
            holder.mRadioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedPosition != pos)
                    {
                        if (selectedButton != null)
                        {
                            selectedButton.setChecked(false);
                        }
                        selectedButton = hldr.mRadioButton;
                        selectedPosition = pos;
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return routeList.size();
        }
    }

    private class ClaimRouteViewHolder extends RecyclerView.ViewHolder
    {
        TextView mTextView;
        RadioButton mRadioButton;
        Route mRoute = null;

        public ClaimRouteViewHolder(View itemView)
        {
            super(itemView);
            mTextView = itemView.findViewById(R.id.routeItemText);
            mRadioButton = itemView.findViewById(R.id.routeItemRadio);
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
