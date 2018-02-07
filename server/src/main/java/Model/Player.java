package Model;

/**
 * Created by Brian on 2/1/18.
 */

public class Player {

    private String _username;
    private String _password;
    private String _playerID;
    private int _gameHistoryIndex;

    public Player(String username, String password){
        _username = username;
        _password = password;
        _playerID = "";
        _gameHistoryIndex = 0;
    }


    /**
     * Returns the username for the Player
     *
     * @return the username for the Player
     */
    public String getUsername() {
        return _username;
    }

    /**
     * Sets the username for the Player
     *
     * @param username the new username for the Player
     */
    public void setUsername(String username){
        _username = username;
    }

    /**
     * Returns the password for the Player
     *
     * @return the password for the Player
     */
    public String getPassword() {
        return _password;
    }

    /**
     * Sets the password for the Player
     *
     * @param password the new password for the Player
     */
    public void setPassword(String password){
        _password = password;
    }

    /**
     * Returns the player ID for the Player
     *
     * @return the player ID for the Player
     */
    public String getPlayerID() {
        return _playerID;
    }

    /**
     * Sets the Player ID for the Player
     *
     * @param playerID the new player ID for the Player
     */
    public void setPlayerID(String playerID){
        _playerID = playerID;
    }

    public int getGameHistoryIndex(){
        return _gameHistoryIndex;
    }

    public void setGameHistoryIndex(int historyIndex){
        _gameHistoryIndex = historyIndex;
    }

    /**
     * Returns whether the Object is equal to this Player
     *
     * @param compare an Object to compare to
     *
     * @return true if the Object is equal to the Player, false otherwise
     */
    @Override
    public boolean equals(Object compare){
        if (compare instanceof Player){
            // this is a Player Object, so compare the username and password
            Player comparePlayer = (Player)compare;

            if (!_username.equals(comparePlayer.getUsername())){
                // the usernames are not the same
                return false;
            }

            if (!_password.equals(comparePlayer.getPassword())){
                // the passwords are not the same
                return false;
            }

        }
        else {
            // the compare Object is not a Player, so they are not equal
            return false;
        }

        // if this point is reached, then the Objects are equal
        return true;
    }

    @Override
    public String toString(){
        String returnString = "";

        returnString += "Username:\t\t\t" + _username + "\n";
        returnString += "Password:\t\t\t" + _password + "\n";
        returnString += "PlayerID:\t\t\t" + _playerID + "\n";
        returnString += "Commands Position:\t" + _gameHistoryIndex;


        return returnString;
    }
}
