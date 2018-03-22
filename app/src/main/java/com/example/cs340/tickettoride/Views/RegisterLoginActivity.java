package com.example.cs340.tickettoride.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cs340.tickettoride.R;

import Communication.ClientCommunicator;
import Presenters.IRegisterLoginPresenter;
import Presenters.RegisterLoginPresenter;

public class RegisterLoginActivity extends AppCompatActivity implements IRegisterLoginView, TextWatcher {

    private Button loginButton;
    private Button registerButton;
    private EditText loginUsername;
    private EditText loginPassword;
    private EditText registerUsername;
    private EditText registerPassword;
    private EditText registerPasswordConfirm;

    private Button setIp;
    private EditText ipAddress;

    // We would prefer to pass this in, but since this is the first view launched, the system instantiates it.
    private IRegisterLoginPresenter presenter = new RegisterLoginPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_login);
        setTitle("Register or Login");

        getSubviews();
        setupListeners();
    }

    @Override
    public void onBackPressed() {
        // Do nothing
    }

    private void getSubviews() {
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        loginUsername = findViewById(R.id.loginUsername);
        loginPassword = findViewById(R.id.loginPassword);
        registerUsername = findViewById(R.id.registerUsername);
        registerPassword = findViewById(R.id.registerPassword);
        registerPasswordConfirm = findViewById(R.id.registerPasswordConfirm);
        setIp = findViewById(R.id.setIpAddress);
        ipAddress = findViewById(R.id.ipAddress);
    }

    private void setupListeners() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.loginButtonPressed();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.registerButtonPressed();
            }
        });

        loginUsername.addTextChangedListener(this);
        loginPassword.addTextChangedListener(this);
        registerUsername.addTextChangedListener(this);
        registerPassword.addTextChangedListener(this);
        registerPasswordConfirm.addTextChangedListener(this);

        ipAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ipAddress.getText().toString().isEmpty()) {
                    ClientCommunicator.getInstance().setIPAddress(ipAddress.getText().toString());
                    Toast.makeText(getApplicationContext(), "IP Address set to " + ClientCommunicator.getInstance().getIPAddress(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // IRegisterLoginView

    @Override
    public void setLoginButtonEnabled(boolean enabled) {
        loginButton.setEnabled(enabled);
    }

    @Override
    public String getLoginUsername() {
        return loginUsername.getText().toString();
    }

    @Override
    public void setLoginUsername(String s)
    {
        loginUsername.setText(s);
    }

    @Override
    public String getLoginPassword() {
        return loginPassword.getText().toString();
    }

    @Override
    public void setRegisterButtonEnabled(boolean enabled) {
        registerButton.setEnabled(enabled);
    }

    @Override
    public String getRegisterUsername() {
        return registerUsername.getText().toString();
    }

    @Override
    public void setRegisterUsername(String s)
    {
        registerUsername.setText(s);
    }

    @Override
    public String getRegisterPassword() {
        return registerPassword.getText().toString();
    }

    @Override
    public String getRegisterPasswordConfirm() {
        return registerPasswordConfirm.getText().toString();
    }

    @Override
    public void displayErrorMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void switchToView(Class<?> newViewClass) {
        Intent intent = new Intent(this, newViewClass);
        this.startActivity(intent);
    }

    // TextWatcher interface

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
}