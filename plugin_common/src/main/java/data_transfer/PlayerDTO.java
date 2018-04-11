package data_transfer;

import java.io.Serializable;

/**
 * Created by Brian on 4/2/18.
 */

public class PlayerDTO implements Serializable {

    public String username;
    public String password;
    public boolean isLoggedIn;
}
