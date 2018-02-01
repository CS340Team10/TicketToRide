package common;

/**
 * Created by ephraimkunz on 1/31/18.
 */

public class Results {
    private boolean success;
    private String data;
    private String error;

    public boolean succeeded() {
        return success;
    }

    public String getData() {
        return data;
    }

    public String getError() {
        return error;
    }
}
