package com.example.cs340.tickettoride.Views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;

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
                        if (invisible > 0 && i == stringsFromCards().length - 1) {
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
                if(visible.size() == 0) {
                    button.setEnabled(false);
                }
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

    private CharSequence[] stringsFromCards() {
        List<String> result = new ArrayList<>();
        for(int i = 0; i < visible.size(); ++i) {
            result.add(visible.get(i).getColor().toString());
        }

        if(invisible > 0) {
            result.add(String.format("Choose From Deck (%d)", invisible));
        }

        CharSequence[] cs = result.toArray(new CharSequence[result.size()]);
        return cs;
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
}
