package com.example.cs340.tickettoride.Views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cs340.tickettoride.R;

import java.util.ArrayList;
import java.util.List;

import Presenters.IPickTrainCardPresenter;
import Presenters.PickTrainCardPresenter;
import common.TrainCard;

/**
 * Created by ephraimkunz on 3/3/18.
 */

public class PickTrainCardView implements IPickTrainCardView {
    Activity activity;
    List<TrainCard> visible;
    int invisible;

    TrainCard chosenCard = null;
    boolean choseFacedown = false;
    AlertDialog dialog;

    IPickTrainCardPresenter presenter;

    private void dialogCreateAndShow() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        dialog = builder
                .setTitle("Train Card Deck")
                .setSingleChoiceItems(stringsFromCards(), -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 5) {
                            // Picked from facedown
                            choseFacedown = true;
                            chosenCard = null;
                        } else {
                            // Picked faceup
                            chosenCard = visible.get(i);
                            choseFacedown = false;
                        }
                    }
                })
                .setPositiveButton("Choose", null)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // If they've selected a card, send to server.
                        if(chosenCard != null) {
                            presenter.pickedTrainCard(chosenCard);
                        } else if (choseFacedown) {
                            presenter.pickedTrainCard(null);
                        }

                        // Do nothing if they haven't picked anything
                    }
                });
            }
        });
        dialog.show();
    }

    @Override
    public void setVisibleCards(List<TrainCard> cards) {
        visible = cards;
    }

    @Override
    public void setInvisibleCards(int invisible) {
        this.invisible = invisible;
    }

    @Override
    public void dismissView() {
        chosenCard = null;
        choseFacedown = false;
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public PickTrainCardView() {
        visible = new ArrayList<>();
        invisible = 0;
    }

    private String[] stringsFromCards() {
        String[] result = new String[6];
        for(int i = 0; i < 5; ++i) {
            if (i > visible.size() - 1) {
                // Not initialized yet
                result[i] = String.format("Fake Card %d", i + 1);
            } else {
                result[i] = visible.get(i).getColor().toString();
            }
        }
        result[5] = String.format("Choose From Deck (%d)", invisible);
        return result;
    }

    @Override
    public void setup(final Activity activity) {
        this.activity = activity;


        Button button = activity.findViewById(R.id.drawTrainCardsButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCreateAndShow();
            }
        });

        // Call this after all setup is done, so that when the presenter refreshes, he can call methods on us without fear.
        presenter = new PickTrainCardPresenter(this);
    }

    public Activity getActivity()
    {
        return activity;
    }

    @Override
    public void showToast(String message)
    {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
