package common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

/**
 * Created by ephraimkunz on 2/21/18.
 */

public class Deck extends Observable implements Serializable {

    public enum ObserverEvents {
        CARD_ADDED, CARD_DRAWN, SHUFFLE, CLEAR
    }

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
     * Adds a List of ICards to the Deck
     *
     * @param cards a List of ICards to add to the Deck
     */
    public void addCards(List<? extends ICard> cards){
        for (int count = 0; count < cards.size(); count++){
            _cards.add(cards.get(count));
        }

        // notify the Observers
        setChanged();
        notifyObservers(ObserverEvents.CARD_ADDED);
    }

    /**
     * Adds another Deck to this Deck
     *
     * @param cards
     */
    public void addCards(Deck cards)
    {
        for (int count = 0; count < cards.size(); count++)
        {
            _cards.add(cards.viewCard(count));
        }

        // notify the Observers
        setChanged();
        notifyObservers(ObserverEvents.CARD_ADDED);
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
     * Removes the first instance of the drawCard from this Deck
     *
     * @param drawCard the card to be drawn from the Deck
     *
     * @return the ICard that was drawn if drawCard was found, null otherwise
     */
    public ICard drawCard(ICard drawCard){
        for (int count = 0; count < _cards.size(); count++){
            if (_cards.get(count).equals(drawCard)){
                // draw the card
                return drawCard(count);
            }
        }

        // if this point is reached, then the card was not found
        return null;
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

            // notify the Observers
            setChanged();
            notifyObservers(ObserverEvents.CARD_DRAWN);

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



        // notify the Observers
        setChanged();
        notifyObservers(ObserverEvents.SHUFFLE);
    }

    /**
     * Removes all ICards from the Deck
     */
    public void clear(){
        _cards.clear();


        // notify the Observers
        setChanged();
        notifyObservers(ObserverEvents.CLEAR);
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
    public List<? extends ICard> toList(Class className){

        ArrayList<ICard> returnValue = new ArrayList<>();

        for (int count = 0; count < _cards.size(); count++){
            returnValue.add(_cards.get(count));
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
