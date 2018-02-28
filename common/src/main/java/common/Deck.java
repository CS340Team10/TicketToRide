package common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ephraimkunz on 2/21/18.
 */

public class Deck {

    private List<ICard> _cards = new ArrayList<ICard>();


    /**
     * Constructs the deck
     */
    public Deck() {

    }

    /**
     * Adds the ICard to the deck
     *
     * @param card the ICard to add to the deck
     */
    public void addCard(ICard card){
        _cards.add(card);
    }

    /**
     * Returns the top card in the deck and removes it
     *
     * @return the top card in the deck
     */
    public ICard drawCard(){
        return drawCard(0);
    }

    /**
     * Returns the card at the specified position in the deck and removes it
     *
     * @param position a number from 0 to (deck size - 1) that specifies the card to be returned
     *
     * @return the ICard at the specified location
     */
    public ICard drawCard(int position){
        if ((position < 0) || (position >= _cards.size())){
            // the position requested is not valid
            return null;
        }
        else {
            ICard returnValue = _cards.get(position);

            // remove the card from the deck
            _cards.remove(position);

            // return the card
            return returnValue;
        }
    }

    /**
     * Returns the top card in the deck
     *
     * @return the top card in the deck
     */
    public ICard viewCard(){
        return viewCard(0);
    }

    /**
     * Returns the card at the specified position in the deck
     *
     * @param position a number from 0 to (deck size - 1) that specifies the card to be returned
     *
     * @return the ICard at the specified location
     */
    public ICard viewCard(int position){
        if ((position < 0) || (position >= _cards.size())){
            // the position requested is not valid
            return null;
        }
        else {
            ICard returnValue = _cards.get(position);

            // return the card
            return returnValue;
        }
    }

    /**
     * Reorders the cards in the deck randomly
     */
    public void shuffle(){
        Collections.shuffle(_cards);
    }

    /**
     * Removes all ICards from the Deck
     */
    public void clear(){
        _cards.clear();
    }

    /**
     * Returns the current size of the Deck
     *
     * @return the current size of the Deck
     */
    public int size(){
        return _cards.size();
    }

    /**
     * Returns the Deck as a List
     */
    public List<?> toList(Class className){

        ArrayList<Object> returnValue = new ArrayList<>();

        for (int count = 0; count < _cards.size(); count++){
            returnValue.add(className.cast(_cards.get(count)));
        }

        return returnValue;
    }

    /**
     * Copies all of the contents of this Deck to the newDeck variable
     *
     * @param newDeck a new Deck to copy the contents of this Deck to
     */
    public void copyToDeck(Deck newDeck){
        for (int count = 0; count < _cards.size(); count++){
            newDeck.addCard(_cards.get(count));
        }
    }

    /**
     * Returns a String representation of the Deck
     *
     * @return a String representation of the Deck
     */
    @Override
    public String toString(){
        String returnString = "";

        for (int count = 0; count < _cards.size(); count++){
            returnString += "-------------------------------------\n";
            returnString += _cards.get(count).toString() + "\n";
            returnString += "-------------------------------------\n";
        }

        return returnString;
    }


}
