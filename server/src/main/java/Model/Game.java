package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brian on 2/1/18.
 */

public class Game {

        private CommandHistory _gameHistory = new CommandHistory();
        private List<Player> _players = new ArrayList<Player>();
        private String _name;
        private int _numOfPlayers;
        private boolean _didStart = false;

        public Game(String name, int numOfPlayers){
            _name = name;
            _numOfPlayers = numOfPlayers;
        }

        /**
         * If the given player is not already participating in the game, then the player is
         *  added to the game
         * @param player the player to add to the game
         *
         * @return true if the Player was added to the game, false otherwise
         */
        public boolean join(Player player){

            if (_numOfPlayers - _players.size() > 0) {

                if (!_players.contains(player)) {
                    // the player has not been added to the game yet
                    _players.add(player);

                    // check if the game should start
                    if (_players.size() == _numOfPlayers){
                        // start the game
                        _gameHistory.addStartGameCommand();
                    }
                    return true;
                }
                else {
                    // the player is already in the game
                    return false;
                }
            }

            // there are already enough players
            return false;
        }

        /**
         *
         * @return whether the game has already started or not
         */
        public boolean hasStarted(){
            return _didStart;
        }

        /**
         * If there are enough players (greater than or equal to the required player count)
         * tell the game that it has started
         * But if there are not enough players, the game will not start and the method will return false
         * @return whether the game successfully started
         */
        public boolean startGame(){
            if (_players.size() >= _numOfPlayers)
            {
                _didStart = true;
            }
            return _didStart;
        }

        public void setName(String name){
            _name = name;
        }

        public String getName(){
            return _name;
        }

        public void setRequiredPlayerCount(int requiredPlayerCount) {
            _numOfPlayers = requiredPlayerCount;
        }

        public int getRequiredPlayerCount() {
            return _numOfPlayers;
        }

        public CommandHistory getGameHistory() {
            return _gameHistory;
        }

        @Override
        public boolean equals(Object o) {
            if (o.getClass() == getClass()) {
                Game g = (Game) o;
                return _name.equals(g.getName())
                        && _numOfPlayers==g.getRequiredPlayerCount()
                        && _gameHistory.equals(g.getGameHistory())
                        && _didStart == g.hasStarted();
            }
            return false;
        }

        @Override
        public String toString(){
            String returnString = "";

            returnString += "Game Name:\t\t\t" + _name + "\n";
            returnString += "Required Players:\t" + _numOfPlayers + "\n";
            returnString += "Empty Spots:\t\t" + (_numOfPlayers - _players.size()) + "\n";
            returnString += "Started:\t\t\t" + hasStarted();

            return returnString;
        }

    }
