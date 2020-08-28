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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
    }

    public void loadUltimosMovimientos(View view) {
        Intent intent = new Intent(this, UltimosMovimientos.class);
        startActivity(intent);
    }

    public void loadRealizarTransferencia(View view) {
        Intent intent = new Intent(this, RealizarTransferencia.class);
        startActivity(intent);
    }

    public void loadMisInversiones(View view) {
        Intent intent = new Intent(this, MisInversiones.class);
        startActivity(intent);
    }

    public void loadRealizarInversiones(View view) {
        Intent intent = new Intent(this, RealizarInversiones.class);
        startActivity(intent);
    }

}
