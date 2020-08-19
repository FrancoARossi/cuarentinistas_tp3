package com.example.cuarentinistas_tp3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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


    private class asyncCall extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... args) {
            //return RESTService.makeGetRequest(ServerAddress.value()+"/rest/movimientos");
            return RESTService.makeGetRequest("https://jsonplaceholder.typicode.com/todos/1");
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject data = new JSONObject(result);
                TableLayout table = (TableLayout) findViewById(R.id.ult_movs_table);
                TableRow row = new TableRow(getApplicationContext());
                for (Iterator key = data.keys(); key.hasNext();) {
                    TextView text1 = new TextView(getApplicationContext());
                    text1.setText(data.get((String) key.next()).toString());
                    text1.setTextColor(Color.BLACK);
                    text1.setTextSize(14);
                    row.addView(text1);
                }
                table.addView(row);
            } catch (JSONException e) {
                //some exception handler code.
            }
        }
    }
}