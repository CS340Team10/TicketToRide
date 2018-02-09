package com.example.cs340.tickettoride.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs340.tickettoride.R;

import Presenters.CreateJoinGamePresenter;
import Presenters.ICreateJoinGamePresenter;

public class CreateJoinGameActivity extends AppCompatActivity implements ICreateJoinGameView {
    private Button createGameButton;
    private EditText newGameName;
    private Spinner newGameNumPlayers;
    private RecyclerView gameList;

    private String[] gameListData = new String[]{};
    private ICreateJoinGamePresenter presenter = new CreateJoinGamePresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_join_game);
        setTitle("Create or Join Game");

        getSubviews();
        setupListeners();
        setupRecyclerView();
        setupSpinner();
    }

    @Override
    public void clearNewGameName() {
        newGameName.setText("");
    }

    @Override
    public void onBackPressed() {
        // Do nothing
    }

    private void getSubviews() {
        createGameButton = findViewById(R.id.createGameButton);
        newGameName = findViewById(R.id.newGameName);
        newGameNumPlayers = findViewById(R.id.newGameNumPlayers);
        gameList = findViewById(R.id.gameList);
    }

    private void setupListeners() {
        createGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.createButtonPressed();
            }
        });

        newGameName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.textChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        newGameNumPlayers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                presenter.textChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void setupRecyclerView() {
        gameList.setLayoutManager(new LinearLayoutManager(this));
        CustomAdapter adapter = new CustomAdapter();
        gameList.setAdapter(adapter);
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.num_players_array, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        newGameNumPlayers.setAdapter(adapter);
    }

    @Override
    public void displayErrorMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void setAvailableGames(String[] gameNames) {
        gameListData = gameNames;
        gameList.getAdapter().notifyDataSetChanged();
    }

    @Override
    public String getNewGameName() {
        return newGameName.getText().toString();
    }

    @Override
    public int getNewGameNumPlayers() {
        return Integer.parseInt(newGameNumPlayers.getSelectedItem().toString());
    }

    @Override
    public void setCreateGameButtonEnabled(boolean enabled) {
        createGameButton.setEnabled(enabled);
    }

    @Override
    public void switchToView(Class<?> newViewClass) {
        Intent intent = new Intent(this, newViewClass);
        this.startActivity(intent);
    }

    private class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private View gameItemCell;

        public CustomViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.gameItemTitle);
            gameItemCell = itemView.findViewById(R.id.gameItemCell);
        }
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(CreateJoinGameActivity.this);
            View view = inflater.inflate(R.layout.game_list_item, parent, false);

            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position) {
            final String title = gameListData[position];
            holder.title.setText(title);
            holder.gameItemCell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.joinGamePressed(title);
                }
            });
        }

        @Override
        public int getItemCount() {
            return gameListData.length;
        }
    }
}

