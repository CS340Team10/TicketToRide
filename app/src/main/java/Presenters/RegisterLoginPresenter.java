package Presenters;

import android.util.Log;

import com.example.cs340.tickettoride.Views.IRegisterLoginView;

import common.Results;

/**
 * Created by ephraimkunz on 1/31/18.
 */

public class RegisterLoginPresenter implements IRegisterLoginPresenter, IPresenter {
    private final String tag = "RegisterLoginPresenter";
    private IRegisterLoginView view;

    public RegisterLoginPresenter(IRegisterLoginView view) {
        this.view = view;
    }

    @Override
    public void loginButtonPressed() {
        // Get login data from view
        // Call into the service
        Log.d(tag, "loginButtonPressed");
    }

    @Override
    public void registerButtonPressed() {
        // Get register data from view
        // Call service method
        Log.d(tag, "registerButtonPressed");
    }

    // Validate input and enable / disable buttons
    @Override
    public void textChanged() {
        view.setLoginButtonEnabled(
                !view.getLoginPassword().isEmpty()
                && !view.getLoginUsername().isEmpty()
        );

        view.setRegisterButtonEnabled(
                !view.getRegisterPassword().isEmpty()
                && !view.getRegisterUsername().isEmpty()
                && !view.getRegisterPasswordConfirm().isEmpty()
                && (view.getRegisterPassword().equals(view.getRegisterPasswordConfirm()))
        );
    }

    @Override
    public void onPostExecute(Results result) {
        // If success, move to next screen. Else show error.
    }
}
