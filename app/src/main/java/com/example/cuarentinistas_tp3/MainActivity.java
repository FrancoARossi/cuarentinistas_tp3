package com.example.cuarentinistas_tp3;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Ejemplo de nueva actividad
    protected void showLayoutTest(View view) {
        Intent intent = new Intent(this, LayoutTest.class);
        startActivity(intent);
    }

    public void restTest(View view) {
        new MiAsyncTask().execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    private class MiAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            return RESTService.makeGetRequest(
                    "https://reqres.in/api/users/2");
        }

        @Override
        protected void onPostExecute(String result) {
            Toast notificacion = Toast.makeText(
                    getApplicationContext(), result, Toast.LENGTH_LONG);
            notificacion.show();
        }
    }
}
