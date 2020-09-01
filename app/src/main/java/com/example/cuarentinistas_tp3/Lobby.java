package com.example.cuarentinistas_tp3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

class ServerAddress {
    public static String value() {
        return "http://192.168.0.217:8080/cuarentinistas";
    }
}

public class Lobby extends AppCompatActivity {
    String cbuCuenta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        Intent alLobby = getIntent();
        cbuCuenta = alLobby.getStringExtra("cbuCuenta");

    }

    public void loadUltimosMovimientos(View view) {
        Intent intent = new Intent(this, UltimosMovimientos.class);
        intent.putExtra("cbuCuenta", cbuCuenta);
        startActivity(intent);
    }

    public void loadRealizarTransferencia(View view) {
        Intent intent = new Intent(this, RealizarTransferencia.class);
        intent.putExtra("cbuCuenta", cbuCuenta);
        startActivity(intent);
    }

}
