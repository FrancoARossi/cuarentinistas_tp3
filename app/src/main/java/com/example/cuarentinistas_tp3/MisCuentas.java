package com.example.cuarentinistas_tp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MisCuentas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Mis Cuentas");
        setContentView(R.layout.activity_mis_cuentas);
    }

    public void loadUltimosMovimientos(View view) {
        Intent intent = new Intent(this, UltimosMovimientos.class);
        startActivity(intent);
    }
}