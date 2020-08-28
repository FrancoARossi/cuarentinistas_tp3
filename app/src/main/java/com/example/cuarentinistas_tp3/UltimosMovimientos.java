package com.example.cuarentinistas_tp3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class UltimosMovimientos extends AppCompatActivity {

    ArrayList<String> listaDatos;
    RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Ultimos Movimientos");
        setContentView(R.layout.activity_ultimos_movimientos);

        recycler = (RecyclerView) findViewById(R.id.recyclerId);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        asyncCall getUltimosMovimientos = new asyncCall();
        getUltimosMovimientos.execute();
    }


    private class asyncCall extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... args) {
            return RESTService.makeGetRequest(ServerAddress.value()+"/rest/movimientos");
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
                JSONArray movimientos = new JSONArray(result);
                listaDatos = new ArrayList<String>();
                for (int i = 0, size = movimientos.length(); i < size; i++) {
                    JSONObject movimiento = movimientos.getJSONObject(i);
                    listaDatos.add(movimiento.toString());
                }
                AdapterDatos adapter = new AdapterDatos(listaDatos);
                recycler.setAdapter(adapter);
            } catch (JSONException e) {
                setContentView(R.layout.rest_error_layout);
            }
        }
    }
}