package com.example.cs340.tickettoride.Views;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cs340.tickettoride.R;

import Presenters.IPickDestCardPresenter;
import Presenters.PickDestCardPresenter;
import common.Deck;

/**
 * Created by Joseph on March 4, 2018
 *
 */
public class PickDestCardView implements IPickDestCardView
{
    private final String TAG = "PickDestCardView";
    private String[] cardChoicesText;
    private boolean[] cardChoicesSelected;
    private Deck offeredCards = null;
    private IPickDestCardPresenter presenter;
    private AlertDialog dialog = null;
    private Context context;
    private int numSelected;
    private int minNumSelected;
    private Button okButton;
    private boolean isShowing = false;

    /**
     * This method should be called before attempting to create and show the dialog
     *
     * @param cards cards that are being offered
     * @param minNumSelected the minimum number of cards the user must select
     */
    @Override
    public void offerDestCards(Deck cards, int minNumSelected)
    {
        if(!isShowing) {
            this.offeredCards = cards;
            cardChoicesText = new String[offeredCards.size()];
            cardChoicesSelected = new boolean[offeredCards.size()];
            for (int i = 0; i < offeredCards.size(); i++) {
                cardChoicesText[i] = offeredCards.viewCard(i).toString();
                cardChoicesSelected[i] = false;
            }
            this.minNumSelected = minNumSelected;
        }
    }

    @Override
    public void dialogCreateAndShow()
    {
        if (!isShowing && context != null && offeredCards != null && minNumSelected <= offeredCards.size())
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Choose Destination cards")
                    .setPositiveButton("OK", new OnPositiveListener())
                    .setMultiChoiceItems(cardChoicesText, cardChoicesSelected, new ChoicesSelectedListener())
                    .setCancelable(false);
            dialog = builder.create();
            dialog.show();
            okButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            okButton.setEnabled(false);
            isShowing = true;
            numSelected = 0;
        }
        else
        {
            if (offeredCards == null)
            {
                showToast("Sorry, you cannot take more Destination Cards.");
            }
            Log.d(TAG, "WARNING: Please offer DestCards before attempting to show the dialog");
        }
    }

    @Override
    public void showToast(String msg) {
        if (context != null)
        {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
        else
        {
            Log.d(TAG, "Could not show Toast because of null Context!");
        }
    }

    /**
     * This method should be called before any other method!
     *
     * @param activity the calling activity
     */
    @Override
    public void setup(Activity activity)
    {
        Button pickDestCardButton = activity.findViewById(R.id.drawDestCardsButton);
        pickDestCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.requestDestCards();
            }
        });
        this.context = activity;
        this.presenter = new PickDestCardPresenter(this);
    }

    private class ChoicesSelectedListener implements DialogInterface.OnMultiChoiceClickListener
    {

        @Override
        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
            cardChoicesSelected[which] = isChecked;
            if (isChecked) {numSelected++;}
            else {numSelected--;}
            if (numSelected >= minNumSelected) {okButton.setEnabled(true);}
            else {okButton.setEnabled(false);}
        }
    }

    private class OnPositiveListener implements DialogInterface.OnClickListener
    {

        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            Deck selectedCards = new Deck();
            for (int i = 0; i < cardChoicesSelected.length; i++)
            {
                if (cardChoicesSelected[i])
                {
                    selectedCards.addCard(offeredCards.viewCard(i));
                }
            }

            presenter.onPickDestCards(selectedCards);
            offeredCards = null;
            isShowing = false;
        }
    }
}
