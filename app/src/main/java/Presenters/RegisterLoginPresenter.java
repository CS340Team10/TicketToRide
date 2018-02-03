package Presenters;

import com.example.cs340.tickettoride.Views.CreateJoinGameActivity;
import com.example.cs340.tickettoride.Views.IRegisterLoginView;

import java.util.Observable;
import java.util.Observer;

import Services.GUIService;
import common.Results;

/**
 * Created by ephraimkunz on 1/31/18.
 */

public class RegisterLoginPresenter implements IRegisterLoginPresenter, IPresenter, Observer {
    private final String tag = "RegisterLoginPresenter";
    private IRegisterLoginView view;

    // Don't allow changing button enable while waiting for a response from the server,
    // or we may get multiple requests and a race condition.
    private boolean waitingForServer = false;

    public RegisterLoginPresenter(IRegisterLoginView view) {
        this.view = view;
        // No need to register an observer here, since we don't need model updates
    }

    @Override
    public void loginButtonPressed() {
        waitingForServer = true;
        view.setLoginButtonEnabled(false);
        view.setRegisterButtonEnabled(false);

        String username = view.getLoginUsername();
        String password = view.getLoginPassword();

        // Call into the service
        GUIService.getInstance().login(this, username, password);
        //onPostExecute(new Results(true, "", "Login failed"));
    }

    @Override
    public void registerButtonPressed() {
        waitingForServer = true;
        view.setLoginButtonEnabled(false);
        view.setRegisterButtonEnabled(false);
        String username = view.getRegisterUsername();
        String password = view.getRegisterPassword();

        // Call service method
        GUIService.getInstance().register(this, username, password);
        //onPostExecute(new Results(false, "", "Register failed"));
    }

    // Validate input and enable / disable buttons
    @Override
    public void textChanged() {
        if (!waitingForServer) {
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
    }

    @Override
    public void onPostExecute(Results result) {
        if (result.succeeded()) {
            // Move to next screen.
            view.switchToView(CreateJoinGameActivity.class);
        } else {
            // Show error
            waitingForServer = false;
            textChanged(); // Re-enable buttons immediately if necessary
            view.displayErrorMessage(result.getError());
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        // Nothing to do here
    }
}
