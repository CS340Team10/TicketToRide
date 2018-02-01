package com.example.cs340.tickettoride.Views;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by ephraimkunz on 1/31/18.
 */

public interface IRegisterLoginView {
    void setLoginButtonEnabled(boolean enabled);
    String getLoginUsername();
    String getLoginPassword();

    void setRegisterButtonEnabled(boolean enabled);
    String getRegisterUsername();
    String getRegisterPassword();
    String getRegisterPasswordConfirm();

    void displayErrorMessage(String message);
    void switchToView(Class<?> newViewClass);
}
