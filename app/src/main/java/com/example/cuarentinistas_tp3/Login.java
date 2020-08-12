package com.example.cuarentinistas_tp3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loadLobby(View view) {
        Intent intent = new Intent(this, Lobby.class);
        startActivity(intent);
    }

}
