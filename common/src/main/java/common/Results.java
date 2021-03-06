package common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ephraimkunz on 1/31/18.
 */

public class Results {
    private boolean success;
    private String data;
    private String error;

    @JsonCreator
    public Results(@JsonProperty("success") boolean success, @JsonProperty("data") String data, @JsonProperty("error") String error) {
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

    @Override
    public String toString(){
        String returnValue = "Success:\t\"" + success + "\"\nData:\t\"" + data + "\"\nError:\t\"" + error + "\"";

        return returnValue;
    }
}
