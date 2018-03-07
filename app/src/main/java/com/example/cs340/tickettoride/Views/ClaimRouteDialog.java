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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Presenters.ClaimRoutePresenter;
import Presenters.IClaimRoutePresenter;
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
    Route selectedRoute = null;
    Map<ICard, Integer> selectedCards = new HashMap<>();
    Set<ICard> disabledNumberPickers = new HashSet<>();
    private final boolean SUBMIT_ENABLED = true;
    private final boolean SUBMIT_DISABLED = false;
    private IClaimRoutePresenter presenter;

    public ClaimRouteDialog() {
        // Required empty public constructor
    }

    public static ClaimRouteDialog newInstance(IClaimRoutePresenter presenter,
                                               List<Route> routes, Map<ICard, Integer> availableCards)
    {
        ClaimRouteDialog dialog = new ClaimRouteDialog();
        dialog.routes = routes;
        dialog.availableCards = availableCards;
        dialog.presenter = presenter;
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
        submitButton.setEnabled(false);
        updateRouteList(routes);
        updateCardCountList(availableCards);
        return v;
    }

    public void disableSubmitButton() {
        if (submitButton != null)
        {
            submitButton.setEnabled(SUBMIT_DISABLED);
        }
    }

    public void enableSubmitButton() {
        if (submitButton != null)
        {
            submitButton.setEnabled(SUBMIT_ENABLED);
        }
    }

    public void disableCardNumberPickers(Set<ICard> cards) {
        disabledNumberPickers.addAll(cards);
        updateCardCountList(availableCards);//refresh recycler view
    }

    public void enableCardNumberPickers(Set<ICard> cards)
    {
        disabledNumberPickers.removeAll(cards);
        updateCardCountList(availableCards);//refresh recycler view
    }

    private class ClaimRouteAdapter extends RecyclerView.Adapter<ClaimRouteViewHolder>
    {
        List<Route> routeList;
        RadioButton lastSelectedButton;

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
            if (selectedRoute == null || selectedRoute == holder.mRoute)
            {
                lastSelectedButton = holder.mRadioButton;
                selectedRoute = holder.mRoute;
                holder.mRadioButton.setChecked(true);
                presenter.onChangeSelection(selectedRoute, selectedCards);
            }
            final ClaimRouteViewHolder hldr = holder;
            holder.mRadioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedRoute!=hldr.mRoute)
                    {
                        if (lastSelectedButton != null)
                        {
                            lastSelectedButton.setChecked(false);
                        }
                        lastSelectedButton = hldr.mRadioButton;
                        selectedRoute = hldr.mRoute;
                        presenter.onChangeSelection(selectedRoute, selectedCards);
                    }
                }
            });
        }

        @Override
        public void onViewRecycled(@NonNull ClaimRouteViewHolder holder) {
            super.onViewRecycled(holder);
            holder.mRadioButton.setChecked(false);
            if (holder.mRadioButton==lastSelectedButton)
            {
                lastSelectedButton = null;
            }
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
            holder.mCard = card;
            holder.mTextView.setText(txt);
            holder.mPicker.setMaxValue(num);
            holder.mPicker.setMinValue(0);
            if (disabledNumberPickers.contains(card))
            {
                holder.mPicker.setEnabled(false);
            }
            else
            {
                holder.mPicker.setEnabled(true);
            }
            final TrainNumberViewHolder hldr = holder;
            holder.mPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    if (newVal == 0 && selectedCards.containsKey(hldr.mCard))
                    {
                        selectedCards.remove(hldr.mCard);
                    }
                    else if (newVal != 0)
                    {
                        selectedCards.put(hldr.mCard, newVal);
                    }
                    presenter.onChangeSelection(selectedRoute, selectedCards);
                }
            });
        }

        @Override
        public int getItemCount() {
            return cardNumList.size();
        }
    }

    private class TrainNumberViewHolder extends RecyclerView.ViewHolder
    {
        ICard mCard;
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
