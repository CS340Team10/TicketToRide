package com.example.cs340.tickettoride.Views;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cs340.tickettoride.R;

import java.util.ArrayList;
import java.util.List;

import Presenters.IHandPresenter;
import common.DestCard;
import common.ICard;
import common.TrainCard;

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
    private RecyclerView.LayoutManager layoutManager;
    private CardAdapter cardAdapter;
    List<ICard> activeCardList = new ArrayList<>();
    List<ICard> otherCardList = new ArrayList<>();

    //private OnFragmentInteractionListener mListener;

    public HandView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HandView.
     */
    /*public static HandView newInstance(String param1, String param2) {
        HandView fragment = new HandView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_hand_view, container, false);
        getSubviews(v);
        return v;
    }

    private void getSubviews(View v)
    {
        tabs = v.findViewById(R.id.handTabs);
        trainTab = tabs.newTab().setText(TRAIN_TAB).setTag(TRAIN_TAB);
        tabs.addTab(trainTab, true);
        destTab = tabs.newTab().setText(DEST_TAB).setTag(DEST_TAB);
        tabs.addTab(destTab, false);
        tabs.addOnTabSelectedListener(this);
        cardRecyclerView = v.findViewById(R.id.handCardRecycler);
        layoutManager = new LinearLayoutManager(v.getContext());
        cardRecyclerView.setLayoutManager(layoutManager);
        cardAdapter = new CardAdapter(activeCardList);
        cardRecyclerView.setAdapter(cardAdapter);
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
        if (isShowingTrainCards())
        {
            activeCardList.clear();
            activeCardList.addAll(cards);
        }
        else
        {
            otherCardList.clear();
            otherCardList.addAll(cards);
        }
    }

    @Override
    public void updateDestHand(List<DestCard> cards)
    {
        if (isShowingDestCards())
        {
            activeCardList.clear();
            activeCardList.addAll(cards);
        }
        else
        {
            otherCardList.clear();
            otherCardList.addAll(cards);
        }
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
    public void showTrainCards() {
        if (isShowingDestCards())
        {
            List<ICard> tempList = activeCardList;
            activeCardList = otherCardList;
            otherCardList = tempList;
        }
    }

    @Override
    public void showDestCards() {
        if (isShowingTrainCards())
        {
            List<ICard> tempList = activeCardList;
            activeCardList = otherCardList;
            otherCardList = tempList;
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

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }*/
}
