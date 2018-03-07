package Presenters;

import common.Deck;

/**
 * Created by Joseph on 3/5/2018.
 */

public interface IPickDestCardPresenter
{
    void onPickDestCards(Deck selectedCards);
    void requestDestCards();
}
