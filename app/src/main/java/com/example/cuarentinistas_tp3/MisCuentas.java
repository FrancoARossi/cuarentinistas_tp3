package com.example.cuarentinistas_tp3;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MisCuentas extends AppCompatActivity {

    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Mis Cuentas");
        setContentView(R.layout.activity_mis_cuentas);
        container = (LinearLayout) findViewById(R.id.containerCuentas);
        asyncCall getMisCuentas = new asyncCall();
        getMisCuentas.execute();
    }


    private class asyncCall extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... args) {
            return RESTService.makeGetRequest(ServerAddress.value() + "/rest/cuentas/cliente/1");
            //return RESTService.makeGetRequest("https://jsonplaceholder.typicode.com/todos");
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                // Si el resultado es un string que contiene el formato de un unico elemento JSON
                // le agrego los corchetes para que cumpla con el formato de un JSONArray
                if (result.charAt(0) != '[') {
                    result = "[" + result + "]";
                }
                JSONArray cuentas = new JSONArray(result);

                for (int i = 0, size = cuentas.length(); i < size; i++) {
                    JSONObject cuenta = cuentas.getJSONObject(i);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    Button btn = new Button(getApplicationContext());
                    btn.setId(i);
                    final int id_ = btn.getId();
                    btn.setText(cuenta.getString("alias"));
                    btn.setBackgroundColor(Color.rgb(0, 209, 178));
                    container.addView(btn, params);
                    btn = (Button) findViewById(id_);
                    btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            startActivity(new Intent(getApplicationContext(), Lobby.class));
                        }
                    });
                }
            } catch (JSONException e) {
                Log.e("ERROR", "Se produjo el siguiente error:", e);
            }
        }
    }

    public void loadMisInversiones(View view) {
    Intent intent = new Intent(this, MisInversiones.class);
    startActivity(intent);
    }
//    public void loadUltimosMovimientos(View view) {
//        Intent intent = new Intent(this, UltimosMovimientos.class);
//        startActivity(intent);
//    }
}

