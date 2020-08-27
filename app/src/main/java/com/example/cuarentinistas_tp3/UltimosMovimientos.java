package com.example.cuarentinistas_tp3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class UltimosMovimientos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ultimos_movimientos);
        asyncCall getUltimosMovimientos = new asyncCall();
        getUltimosMovimientos.execute();
    }


    private class asyncCall extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... args) {
            return RESTService.makeGetRequest(ServerAddress.value()+"/rest/movimientos/1");
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
//                Toast notificacion = Toast.makeText(
//                        getApplicationContext(), result, Toast.LENGTH_LONG);
//                notificacion.show();
                JSONArray movimientos = new JSONArray(result);
                TableLayout table = (TableLayout) findViewById(R.id.ult_movs_table);
                TableRow.LayoutParams params = new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 0, 10, 2);
                for (int i = 0, size = movimientos.length(); i < size; i++) {
                    JSONObject movimiento = movimientos.getJSONObject(i);
                    TableRow row = new TableRow(getApplicationContext());
                    for (Iterator key = movimiento.keys(); key.hasNext(); ) {
                        TextView row_element = new TextView(getApplicationContext());
                        row_element.setText(movimiento.get((String) key.next()).toString());
                        row_element.setTextColor(Color.BLACK);
                        row_element.setTextSize(14);
                        row.addView(row_element);
                    }
                    row.setLayoutParams(params);
                    table.addView(row);
                }
            } catch (JSONException e) {
                setContentView(R.layout.rest_error_layout);
            }
        }
    }
}