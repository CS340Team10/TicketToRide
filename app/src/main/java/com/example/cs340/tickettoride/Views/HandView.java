package com.example.cs340.tickettoride.Views;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cs340.tickettoride.R;

import java.util.ArrayList;
import java.util.List;

import Presenters.HandPresenter;
import Presenters.IHandPresenter;
import common.DestCard;
import common.ICard;
import common.TrainCard;

//import static Testing.TestService.IS_TESTING;

/**
 * Create by Joseph on 27/Feb/2018
 */
public class HandView extends Fragment implements IHandView, TabLayout.OnTabSelectedListener
{
    private final String TRAIN_TAB = "Train Cards";
    private final String DEST_TAB = "Dest Cards";
    private IHandPresenter presenter;
    private boolean showingTrainCards = true;
    private TabLayout tabs;
    private TabLayout.Tab trainTab;
    private TabLayout.Tab destTab;
    private RecyclerView cardRecyclerView;
    private LinearLayoutManager layoutManager;
    private CardAdapter cardAdapter;
    private List<ICard> trainCardList = new ArrayList<>();
    private List<ICard> destCardList = new ArrayList<>();

    //private OnFragmentInteractionListener mListener;

    public HandView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_hand_view, container, false);
        //if you would like to test this call the following: loadTestData()
        getSubviews(v);

        presenter =  new HandPresenter(this);
        return v;
    }

    private void loadTestData()
    {
        destCardList.add(new DestCard("Denver0","Salt Lake City0", 0));
        destCardList.add(new DestCard("Denver1","Salt Lake City1", 1));
        destCardList.add(new DestCard("Denver2","Salt Lake City2", 2));
        destCardList.add(new DestCard("Denver3","Salt Lake City3", 3));
        destCardList.add(new DestCard("Denver4","Salt Lake City4", 4));
        trainCardList.add(new TrainCard(TrainCard.Colors.red));
        trainCardList.add(new TrainCard(TrainCard.Colors.green));
        trainCardList.add(new TrainCard(TrainCard.Colors.blue));
        trainCardList.add(new TrainCard(TrainCard.Colors.black));
        trainCardList.add(new TrainCard(TrainCard.Colors.white));
        destCardList.add(new DestCard("Denver5","Salt Lake City5", 0));
        destCardList.add(new DestCard("Denver1","Salt Lake City6", 1));
        destCardList.add(new DestCard("Denver2","Salt Lake City7", 2));
        destCardList.add(new DestCard("Denver3","Salt Lake City8", 3));
        destCardList.add(new DestCard("Denver4","Salt Lake City9", 4));
        trainCardList.add(new TrainCard(TrainCard.Colors.yellow));
        trainCardList.add(new TrainCard(TrainCard.Colors.purple));
        trainCardList.add(new TrainCard(TrainCard.Colors.wildcard));
        trainCardList.add(new TrainCard(TrainCard.Colors.green));
        trainCardList.add(new TrainCard(TrainCard.Colors.orange));
        destCardList.add(new DestCard("Denver0","Salt Lake City10", 0));
        destCardList.add(new DestCard("Denver1","Salt Lake City11", 1));
        destCardList.add(new DestCard("Denver2","Salt Lake City12", 2));
        destCardList.add(new DestCard("Denver3","Salt Lake City13", 3));
        destCardList.add(new DestCard("Denver4","Salt Lake City14", 4));
        trainCardList.add(new TrainCard(TrainCard.Colors.red));
        trainCardList.add(new TrainCard(TrainCard.Colors.green));
        trainCardList.add(new TrainCard(TrainCard.Colors.blue));
        trainCardList.add(new TrainCard(TrainCard.Colors.black));
        trainCardList.add(new TrainCard(TrainCard.Colors.white));
        destCardList.add(new DestCard("Denver0","Salt Lake City15", 0));
        destCardList.add(new DestCard("Denver1","Salt Lake City16", 1));
        destCardList.add(new DestCard("Denver2","Salt Lake City17", 2));
        destCardList.add(new DestCard("Denver3","Salt Lake City18", 3));
        destCardList.add(new DestCard("Denver4","Salt Lake City19", 4));
        trainCardList.add(new TrainCard(TrainCard.Colors.red));
        trainCardList.add(new TrainCard(TrainCard.Colors.green));
        trainCardList.add(new TrainCard(TrainCard.Colors.blue));
        trainCardList.add(new TrainCard(TrainCard.Colors.black));
        trainCardList.add(new TrainCard(TrainCard.Colors.white));
    }

    private void getSubviews(View v)
    {
        tabs = v.findViewById(R.id.handTabs);
        trainTab = tabs.newTab().setText(TRAIN_TAB).setTag(TRAIN_TAB);
        tabs.addTab(trainTab, showingTrainCards);
        destTab = tabs.newTab().setText(DEST_TAB).setTag(DEST_TAB);
        tabs.addTab(destTab, !showingTrainCards);
        tabs.addOnTabSelectedListener(this);
        cardRecyclerView = v.findViewById(R.id.handCardRecycler);
        cardRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(v.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cardRecyclerView.setLayoutManager(layoutManager);
        if (showingTrainCards)
        {
            cardAdapter = new CardAdapter(trainCardList);
        }
        else
        {
            cardAdapter = new CardAdapter(destCardList);
        }
        cardRecyclerView.setAdapter(cardAdapter);
        cardRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public boolean isShowingTrainCards() {
        return showingTrainCards;
    }

    @Override
    public boolean isShowingDestCards() {
        return !showingTrainCards;
    }

    @Override
    public void updateTrainHand(List<TrainCard> cards)
    {
        trainCardList.clear();
        trainCardList.addAll(cards);
        cardRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void updateDestHand(List<DestCard> cards)
    {
        destCardList.clear();
        destCardList.addAll(cards);
        cardRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab)
    {
        String tabName = (String) tab.getText();
        if (tabName == null) {tabName = TRAIN_TAB;}
        switch (tabName)
        {
            case TRAIN_TAB:
                showTrainCards();
                break;

            case DEST_TAB:
                showDestCards();
                break;
        }
    }

    @Override
    public void showTrainCards()
    {
        if (isShowingDestCards())
        {
            if (!trainTab.isSelected())
            {
                trainTab.select();
            }
            cardAdapter = new CardAdapter(trainCardList);
            cardRecyclerView.swapAdapter(cardAdapter, true);
            showingTrainCards = true;
            cardRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void showDestCards()
    {
        if (isShowingTrainCards())
        {
            if (!destTab.isSelected())
            {
                destTab.select();
            }
            cardAdapter = new CardAdapter(destCardList);
            cardRecyclerView.swapAdapter(cardAdapter, true);
            showingTrainCards = false;
            cardRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab)
    {
        //Do nothing
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab)
    {
        //Do nothing
    }
}
