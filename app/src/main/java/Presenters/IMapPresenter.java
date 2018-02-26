package Presenters;

import ClientModel.Chat;

/**
 * Created by Joseph on 2/21/2018.
 */

public interface IMapPresenter {
    void update();
    void postedChat(Chat message);
    void onShowHistory();
}
