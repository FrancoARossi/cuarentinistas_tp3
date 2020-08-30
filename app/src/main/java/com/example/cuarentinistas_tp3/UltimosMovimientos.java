package com.example.cuarentinistas_tp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class UltimosMovimientos extends AppCompatActivity {

    ArrayList<Movimiento> listaMovimientos;
    RecyclerView recyclerMovimientos;
    Intent myIntent = getIntent();
    String cbuCuenta = myIntent.getStringExtra("cbuCuenta");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Ultimos Movimientos");
        setContentView(R.layout.activity_ultimos_movimientos);

        recyclerMovimientos = (RecyclerView) findViewById(R.id.idRecyclerMovimientos);
        recyclerMovimientos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

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
                listaMovimientos = new ArrayList<>();

                for (int i = 0, size = movimientos.length(); i < size; i++) {
                    JSONObject movimiento = movimientos.getJSONObject(i);
                    Iterator<String> iter = movimiento.keys();
                    String detalle = "";

                    while (iter.hasNext()) {
                        String key = (String) iter.next();
                        String keyFormateada = "";
                        switch (key) {
                            case "cbuDestino":
                                keyFormateada = "CBU Destino";
                                break;
                            case "cbuSalida":
                                keyFormateada = "CBU Salida";
                                break;
                            case "descripcion":
                                keyFormateada = "Descripcion";
                                break;
                            case "fecha":
                                keyFormateada = "Fecha y Hora";
                                break;
                            default:
                                continue;
                        }
                        if (key.equals("fecha")) {
                            SimpleDateFormat dateParse = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                            Date fechaParseada = dateParse.parse(movimiento.get("fecha").toString());
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy, hh:mm:ss");
                            String fecha = dateFormat.format(fechaParseada);
                            detalle += keyFormateada + ": " + fecha + "\n";
                        } else {
                            detalle += keyFormateada + ": " + movimiento.get(key) + "\n";
                        }
                    }
                    Boolean saliente = movimiento.get("cbuSalida").equals(cbuCuenta);
                    String importe = "";
                    if (saliente) {
                        importe = "Importe: -"+movimiento.getString("importe");
                    } else {
                        importe = "Importe: "+movimiento.getString("importe");
                    }
                    listaMovimientos.add(new Movimiento(importe, detalle, saliente));
                }
                AdaptadorMovimientos adapter = new AdaptadorMovimientos(listaMovimientos);
                recyclerMovimientos.setAdapter(adapter);
            } catch (JSONException | ParseException e) {
                Log.e("ERROR", "Se produjo el siguiente error:", e);
            }
        }
    }
}