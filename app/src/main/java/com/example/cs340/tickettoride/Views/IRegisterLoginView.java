package com.example.cs340.tickettoride.Views;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by ephraimkunz on 1/31/18.
 */

public interface IRegisterLoginView {
    void setLoginButtonEnabled(boolean enabled);
    String getLoginUsername();
    void setLoginUsername(String s);
    String getLoginPassword();

    void setRegisterButtonEnabled(boolean enabled);
    String getRegisterUsername();
    void setRegisterUsername(String s);
    String getRegisterPassword();
    String getRegisterPasswordConfirm();

    void displayErrorMessage(String message);
    void switchToView(Class<?> newViewClass);
}
