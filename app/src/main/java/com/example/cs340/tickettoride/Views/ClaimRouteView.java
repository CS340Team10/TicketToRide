package com.example.cs340.tickettoride.Views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cs340.tickettoride.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Presenters.ClaimRoutePresenter;
import Presenters.IClaimRoutePresenter;
import common.ICard;
import common.Route;

/**
 * Created by Joseph on 3/5/2018.
 */

public class ClaimRouteView implements IClaimRouteView
{
    List<Route> routes = new ArrayList<>();
    Map<ICard, Integer> availableCards = new HashMap<>();
    ClaimRouteDialog dialog = null;
    AppCompatActivity activity = null;
    IClaimRoutePresenter presenter;

    @Override
    public void offerRoutes(List<Route> routes)
    {
        if (dialog != null)
        {
            dialog.updateRouteList(routes);
        }
        this.routes = routes;
    }

    @Override
    public void setAvailableCards(Map<ICard,Integer> availableCards)
    {
        if (dialog != null)
        {
            dialog.updateCardCountList(availableCards);
        }
        this.availableCards = availableCards;
    }

    @Override
    public void dialogCreateAndShow()
    {
        String DIALOG_TAG = "CLAIM_ROUTE_DIALOG";
        if (dialog == null && activity != null) {
            FragmentManager fm = activity.getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment prev = fm.findFragmentById(R.id.claimRouteFragment);
            if (prev != null)
            {
                ft.remove(prev);
            }
            ClaimRouteDialog newFrag = ClaimRouteDialog.newInstance();
            newFrag.show(ft, DIALOG_TAG);
        }
    }

    @Override
    public void dismissDialog() {
        if (dialog != null)
        {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void setup(AppCompatActivity activity) {
        this.activity = activity;
        presenter = new ClaimRoutePresenter(this);
        Button claimRouteButton = activity.findViewById(R.id.claimRouteButton);
        claimRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCreateAndShow();
                offerRoutes(routes);
                setAvailableCards(availableCards);
            }
        });
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }
}
