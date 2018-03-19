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
import java.util.Set;

import Presenters.ClaimRoutePresenter;
import Presenters.IClaimRoutePresenter;
import common.ICard;
import common.Route;

/**
 * Created by Joseph on 3/5/2018.
 *
 * @invariant Setup should be called once before calling anything else,
 *      and the activity passed in to setup should not be null
 */

public class ClaimRouteView implements IClaimRouteView
{
    List<Route> routes = new ArrayList<>();
    Map<ICard, Integer> availableCards = new HashMap<>();
    ClaimRouteDialog dialog = null;
    AppCompatActivity activity = null;
    IClaimRoutePresenter presenter;

    /**
     * @pre the dialog should have been created and shown before calling this method (call dialogCreateAndShow())
     * @pre the passed in list of routes should NOT be null, but it may or may not be empty
     * @post this view will store the list of passed in routes
     * @post the dialog will update itself to show the offered routes as a list, with an option to select one from the list
     * @param routes a list of routes that the player will be able to select when claiming a route
     */
    @Override
    public void offerRoutes(List<Route> routes)
    {
        if (dialog != null)
        {
            dialog.updateRouteList(routes);
        }
        this.routes = routes;
    }

    /**
     * @pre the map of availableCards should NOT be null, but may or may not be empty
     * @pre the dialog should have been created and shown before calling this method (call dialogCreateAndShow())
     * @post this view will store the map of available cards and their quantities
     * @post the dialog will update itself to show how many of each card the player has
     * @param availableCards a map of cards that the player can use, where the key is the card, and the value is the quantity of that card
     */
    @Override
    public void setAvailableCards(Map<ICard,Integer> availableCards)
    {
        if (dialog != null)
        {
            dialog.updateCardCountList(availableCards);
        }
        this.availableCards = availableCards;
    }

    /**
     * @pre the dialog should have been created and shown before calling this method (call dialogCreateAndShow())
     * @post the submit button in the dialog will be disabled
     */
    @Override
    public void disableSubmitButton() {
        if (dialog != null)
        {
            dialog.disableSubmitButton();
        }
    }

    /**
     * This method is useful to notify the user when a card combination is valid
     * @pre the dialog should have been created and shown before calling this method (call dialogCreateAndShow())
     * @post the submit button in the dialog will be enabled
     */
    @Override
    public void enableSubmitButton() {
        if (dialog != null)
        {
            dialog.enableSubmitButton();
        }
    }

    /**
     * This method is useful to restrict the user from entering invalid combinations of train cards.
     * For example, let's suppose the user has entered in a valid train card combination. You will
     * want to keep them from entering in extra cards now that a valid combination has been entered.
     * So you will disable all number pickers on all the cards not being used. That way, the user
     * can see they entered in a valid combination. They will now be less likely to try to enter in
     * an invalid combination
     * @pre the dialog should have been created and shown before calling this method (call dialogCreateAndShow())
     * @pre the dialog should already have been told what cards and how many of each it can use by calling setAvailableCards()
     * @pre the set of cards to disable should NOT be null, but may or may not be empty
     * @post The number pickers of each given card, if they exist, will be disabled. In other words, the user will no longer be able to change how many of that card to discard when claiming a route.
     * @param cards the set of train cards you no longer want the user to be able to change the number of
     */
    @Override
    public void disableCardNumberPickers(Set<ICard> cards) {
        if (dialog != null) {
            dialog.disableCardNumberPickers(cards);
        }
    }

    /**
     * @pre the dialog should have been created and shown before calling this method (call dialogCreateAndShow())
     * @pre the dialog should already have been told what cards and how many of each it can use by calling setAvailableCards()
     * @pre the set of cards to enable should NOT be null, but may or may not be empty
     * @post The number pickers of each given card, if they exist, will be enabled. In other words, the user will be able to change how many of that card to discard when claiming a route.
     * @param cards the set of train cards you want the user to be able to change the number of
     */
    @Override
    public void enableCardNumberPickers(Set<ICard> cards) {
        if (dialog != null) {
            dialog.enableCardNumberPickers(cards);
        }
    }

    /**
     * @pre the dialog should have been created and shown before calling this method (call dialogCreateAndShow())
     * @pre the dialog should already have been told what cards and how many of each it can use by calling setAvailableCards()
     * @post The number pickers of all cards, will be enabled. In other words, the user will be able to change how many of each card to discard when claiming a route.
     */
    @Override
    public void enableCardNumberPickers() {
        if (availableCards != null && dialog != null)
        {
            dialog.enableCardNumberPickers(availableCards.keySet());
        }
    }

    /**
     * @pre the dialog should have been created and shown before calling this method (call dialogCreateAndShow())
     * @pre the dialog should already have been told what cards and how many of each it can use by calling setAvailableCards()
     * @post The number pickers of all cards, will be disabled. In other words, the user will no longer be able to change how many of each card to discard when claiming a route.
     */
    @Override
    public void disableCardNumberPickers() {
        if (availableCards != null && dialog != null)
        {
            dialog.disableCardNumberPickers(availableCards.keySet());
        }
    }

    /**
     * @pre the dialog should not have been created and shown yet OR it should have been dismissed
     * @post the dialog showing routes and cards to use will be shown
     */
    @Override
    public void dialogCreateAndShow()
    {
        String DIALOG_TAG = "CLAIM_ROUTE_DIALOG";
        dismissDialog();//If the dialog wasn't properly dismissed, dismiss it
        if (dialog == null && activity != null) {
            FragmentManager fm = activity.getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment prev = fm.findFragmentById(R.id.claimRouteFragment);
            if (prev != null)
            {
                ft.remove(prev);
            }
            dialog = ClaimRouteDialog.newInstance(presenter, routes,availableCards);
            dialog.show(ft, DIALOG_TAG);
        }
    }

    /**
     * @pre the dialog should have already been created and shown
     * @post the dialog will be dismissed and go away. You should now be able to create and show it again if desired.
     */
    @Override
    public void dismissDialog() {
        if (dialog != null)
        {
            dialog.dismiss();
            dialog = null;
        }
    }

    /**
     * @pre a calling activity should have already been created and should be currently "alive"
     * @post this view will attach itself to the activity, create a new presenter and set a listener on the claim route button which will call the presenter
     * @param activity the activity to attach this view to
     */
    @Override
    public void setup(AppCompatActivity activity) {
        this.activity = activity;
        presenter = new ClaimRoutePresenter(this);
        Button claimRouteButton = activity.findViewById(R.id.claimRouteButton);
        claimRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickClaimRoute();
            }
        });
    }

    /**
     * Simply allows the attached presenter to easily make and show toasts
     * @param msg the message to show
     */
    @Override
    public void showToast(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }
}
