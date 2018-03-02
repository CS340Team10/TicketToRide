package Presenters;

import com.example.cs340.tickettoride.Views.IChatHistoryView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import ClientModel.Chat;
import ClientModel.ClientModel;
import Services.GamePlayService;
import common.Results;

/**
 * Created by ephraimkunz on 3/2/18.
 */

public class ChatHistoryPresenter implements IChatHistoryPresenter, IPresenter, Observer {
    IChatHistoryView view;

    public ChatHistoryPresenter(IChatHistoryView view) {
        this.view = view;
        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public void postedChat(String message) {
        if (!message.isEmpty()) {
            GamePlayService.getInstance().sendChat(this, message);
        }
    }

    @Override
    public void onPostExecute(Results result) {
        // Chat posted succeeded or failed
    }

    @Override
    public void update(Observable observable, Object o) {
        // Update history and chat lists
        ArrayList<Chat> chats = ClientModel.getInstance().getChatHistory().getHistory();
        List<String> stringChats = new ArrayList<>();
        for (Chat chat : chats) {
            stringChats.add(chat.getMessage() + ": " + chat.getPlayerID());
        }

        ArrayList<String> hist = ClientModel.getInstance().getGameHistory().getHistory();

        view.updateChatHistory(stringChats);
        view.updateGameHistory(hist);
    }
}
