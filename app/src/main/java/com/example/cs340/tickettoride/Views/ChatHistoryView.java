package com.example.cs340.tickettoride.Views;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cs340.tickettoride.R;

import java.util.ArrayList;
import java.util.List;

import Presenters.ChatHistoryPresenter;
import Presenters.IChatHistoryPresenter;

/**
 * Created by ephraimkunz on 3/2/18.
 */

public class ChatHistoryView implements IChatHistoryView {
    enum Tabs {
        ChatTab,
        HistTab
    }

    Activity activity;
    IChatHistoryPresenter presenter;

    RecyclerView history_chat_list;
    List<String> chat_list = new ArrayList<>();
    List<String> hist_list = new ArrayList<>();
    List<String> active_list = chat_list; // Currently displayed
    Tabs currentTab = Tabs.ChatTab;

    @Override
    public void setup(Activity activity) {
        this.activity = activity;

        history_chat_list = activity.findViewById(R.id.history_chat_list);
        setupTabs();
        presenter = new ChatHistoryPresenter(this);
    }

    private void setupTabs() {
        TabLayout rightTabs = activity.findViewById(R.id.right_drawer_tabs);
        TabLayout.Tab chatTab = rightTabs.newTab().setText("Chat").setTag(Tabs.ChatTab);
        final TabLayout.Tab histTab = rightTabs.newTab().setText("Hist").setTag(Tabs.HistTab);
        rightTabs.addTab(chatTab, 0, true);
        rightTabs.addTab(histTab, 1);

        final Button chatSubmit = activity.findViewById(R.id.newChatSubmitButton);
        final EditText newChat = activity.findViewById(R.id.newChatText);

        history_chat_list.setLayoutManager(new LinearLayoutManager(activity));
        history_chat_list.setAdapter(new ChatHistoryView.CustomAdapter());

        chatSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Send chat message via presenter
                presenter.postedChat(newChat.getText().toString());
            }
        });

        rightTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentTab = (Tabs)tab.getTag();

                if (tab.getTag().equals(Tabs.ChatTab)) {
                    chatSubmit.setVisibility(View.VISIBLE);
                    newChat.setVisibility(View.VISIBLE);

                    // Load recycler with chats
                    active_list = chat_list;
                    history_chat_list.getAdapter().notifyDataSetChanged();

                } else {
                    chatSubmit.setVisibility(View.GONE);
                    newChat.setVisibility(View.GONE);

                    // Load recycler with history
                    active_list = hist_list;
                    history_chat_list.getAdapter().notifyDataSetChanged();

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }


    @Override
    public void updateGameHistory(List<String> hist) {
        hist_list = hist;
        if (currentTab == Tabs.HistTab) {
            active_list = hist_list;
        }
        history_chat_list.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void updateChatHistory(List<String> chat) {
        chat_list = chat;
        if (currentTab == Tabs.ChatTab) {
            active_list = chat_list;
        }
        history_chat_list.getAdapter().notifyDataSetChanged();
    }

    private class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView stringTitle;
        private View stringCell;

        public CustomViewHolder(View itemView) {
            super(itemView);
            stringTitle = itemView.findViewById(R.id.stringTitle);
            stringCell = itemView.findViewById(R.id.stringCell);
        }
    }

    private class CustomAdapter extends RecyclerView.Adapter<ChatHistoryView.CustomViewHolder> {

        @Override
        public ChatHistoryView.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(activity);
            View view = inflater.inflate(R.layout.string_display_item, parent, false);

            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ChatHistoryView.CustomViewHolder holder, int position) {
            final String title = active_list.get(position);
            holder.stringTitle.setText(title);
        }

        @Override
        public int getItemCount() {
            return active_list.size();
        }
    }
}
