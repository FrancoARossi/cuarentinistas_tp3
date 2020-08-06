package com.example.cuarentinistas_tp3;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    // Usar la IPv4 correspondiente a la PC que se encuentra ejecutando el servidor REST.
    String localServerAdress = "ACA-VA-LA-IPv4:8080";

    // Ejemplo de nueva actividad
//    protected void showLayoutTest(View view) {
//        Intent intent = new Intent(this, LayoutTest.class);
//        startActivity(intent);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getClient1(View view) {
        new restCall().execute("GET", "http://"+localServerAdress+"/cuarentinistas/rest/clientes/1");
    }

    public void restTest(View view) {
        new restCall().execute("GET", "https://jsonplaceholder.typicode.com/todos/1");
    }

    private class restCall extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... args) {
            String callType = args[0];
            String url = args[1];

            switch (callType) {
                case "GET":
                    return RESTService.makeGetRequest(url);
                case "POST":
                    return RESTService.callREST(url, "POST", null);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast notification = Toast.makeText(
                    getApplicationContext(), result, Toast.LENGTH_LONG);
            notification.show();
        }
    }
}
