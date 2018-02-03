package common;

/**
 * Created by ephraimkunz on 1/31/18.
 */

public class Results {
    private boolean success;
    private String data;
    private String error;

    public Results(boolean success, String data, String error) {
        this.success = success;
        this.data = data;
        this.error = error;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

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
